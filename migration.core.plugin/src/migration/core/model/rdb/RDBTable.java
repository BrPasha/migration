package migration.core.model.rdb;

import java.util.ArrayList;
import java.util.List;

public class RDBTable {
	
	private final String m_name;
	private String m_remarks;
	private List<RDBColumn> m_columns;
	private RDBPrimaryKey m_primaryKey;
	
	private int m_hashCode;
		
	public RDBTable(String name, String remarks, List<RDBColumn> columns) {
		m_name = name;
		m_hashCode = calculateHashCode();
		m_remarks = remarks;
		m_columns = new ArrayList<>(columns);
	}
	
	public String getName() {
		return m_name;
	}
	
	public List<RDBColumn> getColumns() {
		return m_columns;
	}
	
	public void setColumns(List<RDBColumn> columns) {
		m_columns = columns;
	}
	
	public String getRemarks() {
		return m_remarks;
	}
	
	public void setRemarks(String remarks) {
		m_remarks = remarks;
	}
	
	public void setPrimaryKey(RDBPrimaryKey primaryKey) {
		m_primaryKey = primaryKey;
	}
	
	public RDBPrimaryKey getPrimaryKey() {
		return m_primaryKey;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int hashCode() {
		return m_hashCode;
	}

	private int calculateHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_name == null) ? 0 : m_name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RDBTable other = (RDBTable) obj;
		if (m_name == null) {
			if (other.m_name != null)
				return false;
		} else if (!m_name.equals(other.m_name))
			return false;
		return true;
	}
}
