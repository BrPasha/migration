package migration.core.model.rdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RDBColumn {
	
	private String m_name;

	@JsonProperty("name")
	public String getName() {
		return m_name;
	}
	
	@JsonProperty("name")
	public void setName(String name) {
		m_name = name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
