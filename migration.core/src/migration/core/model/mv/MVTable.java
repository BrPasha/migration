package migration.core.model.mv;

import java.util.ArrayList;
import java.util.List;

import migration.core.model.rdb.RDBTable;

public class MVTable {
	private List<RDBTable> m_sources = new ArrayList<>();

	public MVTable(List<RDBTable> sources) {
		this.m_sources.addAll(sources);
	}
	
	public MVTable(RDBTable base) {
		this.m_sources.add(base);
	}

	public MVTable(RDBTable base, List<RDBTable> related) {
		this.m_sources.add(base);
		this.m_sources.addAll(related);
	}

	public List<RDBTable> getSourceTables() {
		return m_sources;
	}
	
	@Override
	public String toString() {
		return m_sources.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_sources == null) ? 0 : m_sources.hashCode());
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
		MVTable other = (MVTable) obj;
		if (m_sources == null) {
			if (other.m_sources != null)
				return false;
		} else if (!m_sources.equals(other.m_sources))
			return false;
		return true;
	}
}
