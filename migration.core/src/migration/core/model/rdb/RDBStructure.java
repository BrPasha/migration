package migration.core.model.rdb;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RDBStructure {
	
	private List<RDBTable> m_tables;
	private List<RDBRelation> m_relations;
	
	public RDBStructure() {
	}
	
	@JsonProperty("relations")
	public List<RDBRelation> getRelations() {
		return m_relations;
	}
	
	@JsonProperty("tables")
	public List<RDBTable> getTables() {
		return m_tables;
	}
	
	@JsonProperty("relations")
	public void setRelations(List<RDBRelation> relations) {
		m_relations = relations;
	}
	
	@JsonProperty("tables")
	public void setTables(List<RDBTable> tables) {
		m_tables = tables;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Tables:%s\nRelations:%s", 
				String.join("\n\t", getTables().stream().map(t -> t.toString()).collect(Collectors.toList())), 
				String.join("\n\t", getRelations().stream().map(r -> r.toString()).collect(Collectors.toList())));
	}
}
