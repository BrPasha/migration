package migration.core.model.mv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import migration.core.db.multivalue.IMVMetadataProvider;

public class MVFile implements IMVMetadataProvider {
	private String m_name;
	private List<MVField> m_columns = new ArrayList<>();
	private List<String> m_sourceTables;

	public MVFile(String name, List<MVField> columns, List<String> sourceTables) {
		m_name = name;
		m_sourceTables = sourceTables;
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
	
	public List<String> getSourceTables() {
		return m_sourceTables;
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
	
	@Override
	public MVColumnDepth getDepth(String fieldName) {
		return MVColumnDepth.resolve(getFields().stream().filter(fld -> fld.getName().equals(fieldName)).findFirst().get().getDepth());
	}
	
	@Override
	public MVColumnDepth getDepth(int fieldIndex) {
		return MVColumnDepth.resolve(m_columns.get(fieldIndex).getDepth());
	}
}
