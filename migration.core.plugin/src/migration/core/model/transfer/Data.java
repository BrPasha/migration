package migration.core.model.transfer;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import asjava.uniclientlibs.UniDynArray;
import asjava.uniclientlibs.UniString;
import asjava.uniclientlibs.UniStringException;
import asjava.uniclientlibs.UniTokens;
import asjava.uniobjects.UniSession;
import migration.core.db.multivalue.IMVMetadataProvider;
import migration.core.db.multivalue.IMVResultSet;
import migration.core.db.relational.IDatabaseClient;
import migration.core.db.relational.ProviderException;
import migration.core.model.mv.MVColumnDepth;
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
	
	private String generateBaseTableSql() {
		StringBuilder strbuf = new StringBuilder();
		strbuf.append("SELECT ");
		strbuf.append(String.join(",",
				m_transfer.getBaseTable().getColumns().stream()
						.map(col -> col.getName()).collect(Collectors.toList())));
		strbuf.append(" FROM ");
		strbuf.append(m_transfer.getBaseTable().getName());
		strbuf.append(" ORDER BY ");
		strbuf.append(String.join(",", m_transfer.getBaseTable().getPrimaryKey().getColumns()));
		return strbuf.toString();
	}
	
	private List<String> generateEmbeddedTablesSql() {
		List<String> result = new ArrayList<>();
		for (RDBTable table : m_transfer.getEmbeddedTables()) {
			RDBRelation relation = Transfer.findRelation(m_transfer.getStructure().getRelations(), m_transfer.getBaseTable(), table);
			String leftCol = relation.matchFirst(m_transfer.getBaseTable().getName()) ? relation.getColumn1() : relation.getColumn2();
			String rightCol = relation.matchFirst(m_transfer.getBaseTable().getName()) ? relation.getColumn2() : relation.getColumn1();
			StringBuilder strbuf = new StringBuilder();
			strbuf.append("SELECT ");
			strbuf.append(String.join(",",
					table.getColumns().stream()
							.map(col -> "B." + col.getName()).collect(Collectors.toList())));
			strbuf.append(" FROM ");
			strbuf.append(m_transfer.getBaseTable().getName());
			strbuf.append(" A ");
			strbuf.append(" LEFT JOIN ");
			strbuf.append(table.getName());
			strbuf.append(" B ");
			strbuf.append(" ON ");
			strbuf.append("A" + "." + leftCol);
			strbuf.append("=");
			strbuf.append("B" + "." + rightCol);
			strbuf.append(" ORDER BY ");
			strbuf.append("A" + "." + leftCol);
			result.add(strbuf.toString());
		}
		return result;
	}
	
	private String generateSql() {
		StringBuilder strbuf = new StringBuilder();
		strbuf.append("SELECT ");
		String baseCorrelative = "A";
		int idx = 1;
		strbuf.append(String.join(",",
				m_transfer.getBaseTable().getColumns().stream()
						.map(col -> baseCorrelative + "." + col.getName()).collect(Collectors.toList())));
		for (RDBTable embTable : m_transfer.getEmbeddedTables()) {
			strbuf.append(",");
			String correlative = new String(new char[] {(char)('A' + idx++)});
			strbuf.append(String.join(",", embTable.getColumns().stream()
					.map(col -> correlative + "." + col.getName()).collect(Collectors.toList())));
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
			strbuf.append(" ORDER BY ");
			strbuf.append(String.join(",", groupColumns.stream().map(col -> baseCorrelative + "." + col).collect(Collectors.toList())));
		}
		return strbuf.toString();
	}
	
	public Record nextRecord(UniSession session, IMVMetadataProvider metadataProvider) throws ProviderException {
		try {
			if (m_baseTableResultSet == null) {
				
				System.out.println(generateBaseTableSql());
				System.out.println(generateEmbeddedTablesSql());
				System.out.println();
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
			TreeMap<Integer, List<String>> values = new TreeMap<>();
			toValues(recordData, values, metadataProvider);
			while (m_baseTableResultSet.next()) {
				if (recordIdParts.equals(extractRecordIdParts())) {
					UniDynArray nextDataPart = record(session);
					toValues(nextDataPart, values, metadataProvider);
				} else {
					break;
				}
			}
			String data = String.join(UniTokens.AT_FM, values.values().stream()
					.map(lst -> String.join(UniTokens.AT_VM, lst)).collect(Collectors.toList()));
			return new Record(String.join(",", recordIdParts), session.dynArray(data));
		} catch (SQLException ex) {
			throw new ProviderException(ex);
		}
	}

	private void toValues(UniDynArray recordData, Map<Integer, List<String>> values, IMVMetadataProvider metadataProvider) {
		for (int i = 1; i < recordData.dcount() + 1; i++) {
			List<String> value = values.get(i);
			if (value == null) {
				value = new ArrayList<>();
				values.put(i, value);
			}
			if (metadataProvider.getDepth(i - 1) == MVColumnDepth.multivalue
					|| value.isEmpty()) {
				value.add(recordData.extract(i).toString());
			}
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
		} else if (column.getDataType() == Types.BLOB) {
			Blob blob = m_baseTableResultSet.getBlob(relIdx);
			record.replace(mvIdx++, "");
		} else if (column.getDataType() == Types.DATE) {
			Date timestamp = m_baseTableResultSet.getDate(relIdx);
			if (m_baseTableResultSet.wasNull()) {
				record.replace(mvIdx++, "");
			} else {
				UniString internalDate = session.iconv(
						DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US).format(timestamp), 
						Transfer.DATE_CONF_CODE);
				record.replace(mvIdx++, internalDate);
			}
		} else if (column.getDataType() == Types.TIME) {
			Date timestamp = m_baseTableResultSet.getDate(relIdx);
			if (m_baseTableResultSet.wasNull()) {
				record.replace(mvIdx++, "");
			} else {
				String time = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.US).format(timestamp);
				UniString internalTime = session.iconv(
						time.substring(0, time.length() - 3), 
						Transfer.TIME_CONV_CODE);
				record.replace(mvIdx++, internalTime);
			}
		} else if (column.getDataType() == Types.DECIMAL) {
			BigDecimal bd = m_baseTableResultSet.getBigDecimal(relIdx);
			NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
			numberFormat.setMinimumFractionDigits(column.getDecimalDigits());
			UniString val = session.iconv(
					numberFormat.format(bd), 
					"MR" + column.getDecimalDigits());
			record.replace(mvIdx++, val);
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
