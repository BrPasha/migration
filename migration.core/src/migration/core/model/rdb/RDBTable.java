package migration.core.model.rdb;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RDBTable {
	
	private String m_name;
	private List<RDBColumn> m_columns;
	
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
	
	@Override
	public String toString() {
		return String.format("%s[%s]", getName(), getColumns());
	}
}
