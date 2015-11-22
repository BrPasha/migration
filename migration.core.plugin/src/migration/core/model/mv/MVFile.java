package migration.core.model.mv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MVFile {
	private String m_name;
	private List<MVField> m_columns = new ArrayList<>();

	public MVFile(String name, List<MVField> columns) {
		m_name = name;
		m_columns = new ArrayList<>(columns);
	}

	public String getName() {
		return m_name;
	}

	public List<MVField> getFields() {
		return Collections.unmodifiableList(m_columns);
	}

	public void setColumns(List<MVField> columns) {
		m_columns = new ArrayList<>(columns);
	}

	public void setName(String name) {
		m_name = name;
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
		MVFile other = (MVFile) obj;
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

	@Override
	public String toString() {
		return getName();
	}
}
