package migration.core.model.rdb;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RDBTable {
	
	private String m_name;
	private String m_remarks;
	private List<RDBColumn> m_columns;
	private RDBPrimaryKey m_primaryKey;
	
	public RDBTable() {
	}
	
	public RDBTable(String name, String remarks, List<RDBColumn> columns) {
		m_name = name;
		m_remarks = remarks;
		m_columns = new ArrayList<>(columns);
	}
	
	@JsonProperty("name")
	public String getName() {
		return m_name;
	}
	
	@JsonProperty("name")
	public void setName(String name) {
		m_name = name;
	}
	
	@JsonProperty("columns")
	public List<RDBColumn> getColumns() {
		return m_columns;
	}
	
	@JsonProperty("columns")
	public void setColumns(List<RDBColumn> columns) {
		m_columns = columns;
	}
	
	@JsonProperty("remarks")
	public String getRemarks() {
		return m_remarks;
	}
	
	@JsonProperty("remarks")
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_columns == null) ? 0 : m_columns.hashCode());
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
		if (m_columns == null) {
			if (other.m_columns != null)
				return false;
		} else if (!m_columns.equals(other.m_columns))
			return false;
		if (m_name == null) {
			if (other.m_name != null)
				return false;
		} else if (!m_name.equals(other.m_name))
			return false;
		return true;
	}
}