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
	
	@Override
	public int hashCode() {
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
		RDBColumn other = (RDBColumn) obj;
		if (m_name == null) {
			if (other.m_name != null)
				return false;
		} else if (!m_name.equals(other.m_name))
			return false;
		return true;
	}
}
