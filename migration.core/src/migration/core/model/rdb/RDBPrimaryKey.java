package migration.core.model.rdb;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class RDBPrimaryKey {
	private String m_catalog;
	private String m_schema;
	private String m_table;
	private String m_name;
	private List<String> m_columns;
	
	public RDBPrimaryKey(String catalog, String schema, String table, String name, List<String> columns) {
		m_catalog = catalog;
		m_schema = schema;
		m_table = table;
		m_name = name;
		m_columns = new ArrayList<>(columns);
	}
	
	public String getName() {
		return m_name;
	}
	
	public String getTable() {
		return m_table;
	}
	
	public List<String> getColumns() {
		return m_columns;
	}
	
	public String getCatalog() {
		return m_catalog;
	}
	
	public String getSchema() {
		return m_schema;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("PK:{0}[{1}({2})]", getName(), getTable(), getColumns());
	}
}
