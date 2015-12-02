package migration.core.model.rdb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RDBRelation {
	private String m_column1;
	private String m_table1;
	private String m_column2;
	private String m_table2;
	private RDBRelationType m_relationType;
	
	@JsonCreator
	public RDBRelation(
			@JsonProperty("table1") String table1, 
			@JsonProperty("column1") String column1, 
			@JsonProperty("table2") String table2,
			@JsonProperty("column2") String column2,
			@JsonProperty("relationType") RDBRelationType relationType) {
		m_column1 = column1;
		m_table1 = table1;
		m_column2 = column2;
		m_table2 = table2;
		m_relationType = relationType;
	}
	
	@JsonProperty("column1")
	public String getColumn1() {
		return m_column1;
	}
	
	@JsonProperty("column2")
	public String getColumn2() {
		return m_column2;
	}
	
	@JsonProperty("table1")
	public String getTable1() {
		return m_table1;
	}
	
	@JsonProperty("table2")
	public String getTable2() {
		return m_table2;
	}
	
	@JsonProperty("relationType")
	public RDBRelationType getRelationType() {
		return m_relationType;
	}
	
	public boolean match(String table) {
		return matchFirst(table) || matchSecond(table);
	}
	
	public boolean match(String table1, String table2) {
		return matchFirst(table1) && matchSecond(table2);
	}
	
	public boolean matchFirst(String table) {
		return getTable1().equals(table);
	}
	
	public boolean matchSecond(String table) {
		return getTable2().equals(table);
	}
	
	@Override
	public String toString() {
		String connector = "----";
		switch (getRelationType()) {
		case primaryToForeign:
			connector = "<---";
			break;
		case primaryToPrimary:
			connector = "----";
			break;
		case oneToOne:
			connector = "1--1";
			break;
		case oneToMany:
			connector = "1--N";
			break;
		case manyToOne:
			connector = "N--1";
			break;
		case manyToMany:
			connector = "N--N";
			break;
		default:
			break;
		}
		return String.format("%s(%s) %s %s(%s)", 
				getTable1(), getColumn1(), 
				connector, 
				getTable2(), getColumn2());
	}
}
