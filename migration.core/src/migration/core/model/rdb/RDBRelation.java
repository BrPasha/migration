package migration.core.model.rdb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RDBRelation {
	
	private String m_columnPrimary;
	private String m_tablePrimary;
	
	private String m_columnForeign;
	private String m_tableForeign;
	
	@JsonCreator
	public RDBRelation(
			@JsonProperty("columnPrimary") String columnPrimary, 
			@JsonProperty("tablePrimary") String tablePrimary, 
			@JsonProperty("columnForeign") String columnForeign, 
			@JsonProperty("tableForeign") String tableForeign) {
		m_columnPrimary = columnPrimary;
		m_tablePrimary = tablePrimary;
		m_columnForeign = columnForeign;
		m_tableForeign = tableForeign;
	}
	
	@JsonProperty("columnForeign")
	public String getColumnForeign() {
		return m_columnForeign;
	}
	
	@JsonProperty("columnPrimary")
	public String getColumnPrimary() {
		return m_columnPrimary;
	}
	
	@JsonProperty("tableForeign")
	public String getTableForeign() {
		return m_tableForeign;
	}
	
	@JsonProperty("tablePrimary")
	public String getTablePrimary() {
		return m_tablePrimary;
	}
	
	@Override
	public String toString() {
		return String.format("%s(%s) <- %s(%s)", getTablePrimary(), getColumnPrimary(), getTableForeign(), getColumnForeign());
	}
}
