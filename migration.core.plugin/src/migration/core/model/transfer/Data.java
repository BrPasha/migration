package migration.core.model.transfer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import asjava.uniclientlibs.UniDynArray;
import asjava.uniclientlibs.UniString;
import asjava.uniclientlibs.UniStringException;
import asjava.uniobjects.UniSession;
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
	
	public Record nextRecord(UniSession session) throws ProviderException {
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
			UniDynArray recordData = record(session);
			while (m_baseTableResultSet.next()) {
				if (recordIdParts.equals(extractRecordIdParts())) {
					UniDynArray nextDataPart = record(session);
					for (int i = 0; i < nextDataPart.dcount(); i++) {
						UniDynArray cell = nextDataPart.extract(i);
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
	
	private UniDynArray record(UniSession session) throws ProviderException {
		try {
			UniDynArray record = new UniDynArray(session, "");
			int mvIdx = 1;
			int relIdx = 1;
			for (RDBColumn column : m_transfer.getBaseTable().getColumns()) {
				mvIdx = writeColumn(relIdx++, session, record, mvIdx, column);
			}
			for (RDBTable table : m_transfer.getEmbeddedTables()) {
				for (RDBColumn column : table.getColumns()) {
					mvIdx = writeColumn(relIdx++, session, record, mvIdx, column);
				}
			}
			return record;
		} catch (SQLException ex) {
			throw new ProviderException(ex);
		} catch (UniStringException ex) {
			throw new ProviderException(ex);
		}
	}

	private int writeColumn(int relIdx, UniSession session, UniDynArray record, int mvIdx, RDBColumn column)
			throws SQLException, UniStringException {
		if (column.getDataType() == Types.TIMESTAMP) {
			Timestamp timestamp = m_baseTableResultSet.getTimestamp(relIdx);
			if (m_baseTableResultSet.wasNull()) {
				record.replace(mvIdx++, "");
				record.replace(mvIdx++, "");
			} else {
				UniString internalDate = session.iconv(
						DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US).format(timestamp), 
						Transfer.DATE_CONF_CODE);
				record.replace(mvIdx++, internalDate);
				String time = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.US).format(timestamp);
				UniString internalTime = session.iconv(
						time.substring(0, time.length() - 3), 
						Transfer.TIME_CONV_CODE);
				record.replace(mvIdx++, internalTime);
			}
		} else {
			String cell = m_baseTableResultSet.getString(relIdx);
			if (m_baseTableResultSet.wasNull()) {
				record.replace(mvIdx++, "");
			} else {
				record.replace(mvIdx++, cell);
			}
		}
		return mvIdx;
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
