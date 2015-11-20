package migration.core.model.mv;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MVStructure {
	private Set<MVFile> m_tables;

	public MVStructure(List<MVFile> tables) {
		m_tables = new HashSet<>(tables);
	}

	public Set<MVFile> getTables() {
		return m_tables;
	}
	
	@Override
	public String toString() {
		return m_tables.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_tables == null) ? 0 : m_tables.hashCode());
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
		MVStructure other = (MVStructure) obj;
		if (m_tables == null) {
			if (other.m_tables != null)
				return false;
		} else if (!m_tables.equals(other.m_tables))
			return false;
		return true;
	}
}
