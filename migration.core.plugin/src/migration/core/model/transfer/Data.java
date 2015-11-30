package migration.core.model.transfer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import asjava.uniclientlibs.UniDynArray;
import migration.core.db.multivalue.IMVResultSet;
import migration.core.db.relational.IDatabaseClient;
import migration.core.db.relational.ProviderException;
import migration.core.model.rdb.RDBColumn;
import migration.core.model.rdb.RDBPrimaryKey;

public class Data implements IMVResultSet, AutoCloseable {
	
	private Transfer m_transfer;
	private IDatabaseClient m_rdbClient;
	
	private ResultSet m_baseTableResultSet;
	
	public Data(Transfer transfer, IDatabaseClient rdbClient) {
		m_transfer = transfer;
		m_rdbClient = rdbClient;
	}
	
	public boolean next() throws ProviderException {
		try {
			if (m_baseTableResultSet == null) {
				String sql = String.format("SELECT %s FROM %s", "*", m_transfer.getBaseTable().getName());
				m_baseTableResultSet = m_rdbClient.executeQuery(sql);
			}
			return m_baseTableResultSet.next();
		} catch (SQLException ex) {
			throw new ProviderException(ex);
		}
	}
	
	public UniDynArray record() throws ProviderException {
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
	
	public String recordId() throws ProviderException {
		try {
			String recordId = null;
			if (m_transfer.getBaseTable().getPrimaryKey() != null) {
				RDBPrimaryKey primaryKey = m_transfer.getBaseTable().getPrimaryKey();
				List<String> recordParts = new ArrayList<>();
				for (String col : primaryKey.getColumns()) {
					recordParts.add(m_baseTableResultSet.getString(col));
				}
				recordId = String.join(" ", recordParts);
			} else {
				recordId = UUID.randomUUID().toString();
			}
			return recordId;
		} catch (SQLException ex) {
			throw new ProviderException(ex);
		}
	}

	@Override
	public void close() throws Exception {
		if (m_baseTableResultSet != null) {
			m_baseTableResultSet.close();
		}
	}
	
}
