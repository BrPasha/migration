package migration.core.model.transfer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import asjava.uniclientlibs.UniDynArray;
import migration.core.db.multivalue.IMVResultSet;
import migration.core.db.relational.IDatabaseClient;
import migration.core.db.relational.ProviderException;
import migration.core.model.rdb.RDBColumn;
import migration.core.model.rdb.RDBPrimaryKey;
import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBTable;

public class Data implements IMVResultSet {
	
	private Transfer m_transfer;
	private IDatabaseClient m_rdbClient;
	
	private ResultSet m_baseTableResultSet;
	
	public Data(Transfer transfer, IDatabaseClient rdbClient) {
		m_transfer = transfer;
		m_rdbClient = rdbClient;
	}
	
	private String generateSql() {
		StringBuilder strbuf = new StringBuilder();
		strbuf.append("SELECT ");
		String baseCorrelative = "A";
		int idx = 1;
		strbuf.append(String.join(",", m_transfer.getBaseTable().getColumns().stream().map(col -> baseCorrelative + "." +col.getName()).collect(Collectors.toList())));
		for (RDBTable embTable : m_transfer.getEmbeddedTables()) {
			strbuf.append(",");
			String correlative = new String(new char[] {(char)('A' + idx++)});
			strbuf.append(String.join(",", embTable.getColumns().stream().map(col -> correlative + "." + col.getName()).collect(Collectors.toList())));
		}
		strbuf.append(" FROM ");
		strbuf.append(m_transfer.getBaseTable().getName());
		strbuf.append(" " + baseCorrelative + " ");
		idx = 1;
		Set<String> groupColumns = new TreeSet<>();
		for (RDBTable embTable : m_transfer.getEmbeddedTables()) {
			strbuf.append(" LEFT JOIN ");
			strbuf.append(embTable.getName());
			String correlative = new String(new char[] {(char)('A' + idx++)});
			strbuf.append(" ");
			strbuf.append(correlative);
			strbuf.append(" ON ");
			RDBRelation relation = Transfer.findRelation(m_transfer.getStructure().getRelations(), m_transfer.getBaseTable(), embTable);
			String leftCol = relation.getTable1().equals(m_transfer.getBaseTable().getName()) ? relation.getColumn1() : relation.getColumn2();
			strbuf.append(baseCorrelative + "." + leftCol);
			strbuf.append("=");
			String rightCol = relation.getTable1().equals(m_transfer.getBaseTable().getName()) ? relation.getColumn2() : relation.getColumn1();
			strbuf.append(correlative + "." + rightCol);
			groupColumns.add(leftCol);
		}
		if (!groupColumns.isEmpty()) {
			strbuf.append(" GROUP BY ");
			strbuf.append(String.join(",", groupColumns.stream().map(col -> baseCorrelative + "." + col).collect(Collectors.toList())));
		}
		return strbuf.toString();
	}
	
	public Record nextRecord() throws ProviderException {
		try {
			if (m_baseTableResultSet == null) {
				String sql = generateSql();
				m_baseTableResultSet = m_rdbClient.executeQuery(sql);
				if (!m_baseTableResultSet.next()) {
					return null;
				}
			}
			if (m_baseTableResultSet.isAfterLast()) {
				return null;
			}
			List<String> recordIdParts = extractRecordIdParts();
			UniDynArray recordData = record();
			while (m_baseTableResultSet.next()) {
				if (recordIdParts.equals(extractRecordIdParts())) {
					UniDynArray nextDataPart = record();
					for (int i = 0; i < nextDataPart.dcount(); i++) {
						String cell = nextDataPart.extract(i).toString();
						if (recordData.extract(i).dcount() > 1 || !recordData.extract(i).equals(cell)) {
							recordData.insert(i, cell);
						}
					}
				} else {
					break;
				}
			}
			return new Record(String.join(",", recordIdParts), recordData);
		} catch (SQLException ex) {
			throw new ProviderException(ex);
		}
	}
	
	private UniDynArray record() throws ProviderException {
		try {
			UniDynArray record = new UniDynArray("");
			int idx = 1;
			for (RDBColumn column : m_transfer.getBaseTable().getColumns()) {
				String cell = m_baseTableResultSet.getString(column.getName());
				if (m_baseTableResultSet.wasNull()) {
					record.replace(idx++, "");
				} else {
					record.replace(idx++, cell);
				}
			}
			return record;
		} catch (SQLException ex) {
			throw new ProviderException(ex);
		}
	}

	private List<String> extractRecordIdParts() throws SQLException {
		List<String> recordParts = new ArrayList<>();
		if (m_transfer.getBaseTable().getPrimaryKey() != null) {
			RDBPrimaryKey primaryKey = m_transfer.getBaseTable().getPrimaryKey();
			for (String col : primaryKey.getColumns()) {
				recordParts.add(m_baseTableResultSet.getString(col));
			}
		} else {
			recordParts.add(UUID.randomUUID().toString());
		}
		return recordParts;
	}

	@Override
	public void close() throws Exception {
		if (m_baseTableResultSet != null) {
			m_baseTableResultSet.close();
		}
	}
	
}
