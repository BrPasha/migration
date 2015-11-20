package migration.core.model.rdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import migration.core.algorithm.graph.Edge;
import migration.core.algorithm.graph.Graph;
import migration.core.algorithm.graph.Vertex;

public class RDBStructure {
	
	private List<RDBTable> m_tables;
	private List<RDBRelation> m_relations;
	
	@JsonCreator
	public RDBStructure(
			@JsonProperty("tables") List<RDBTable> tables, 
			@JsonProperty("relations") List<RDBRelation> relations ) {
		m_tables = tables;
		m_relations = relations;
	}
	
	@JsonProperty("relations")
	public List<RDBRelation> getRelations() {
		return m_relations;
	}
	
	@JsonProperty("tables")
	public List<RDBTable> getTables() {
		return m_tables;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Tables:\n\t%s\nRelations:\n\t%s", 
				String.join("\n\t", getTables().stream().map(t -> t.toString()).collect(Collectors.toList())), 
				String.join("\n\t", getRelations().stream().map(r -> r.toString()).collect(Collectors.toList())));
	}
	
	public Graph<RDBTable, RDBRelation> constructGraph() {
		Graph<RDBTable, RDBRelation> graph = new Graph<>();
		Map<String, Vertex<RDBTable, RDBRelation>> vertices = new HashMap<>();
		m_tables.stream().map(table -> new Vertex<RDBTable, RDBRelation>(table)).forEach(v -> {
			graph.addVertex(v);
			vertices.put(v.getData().getName(), v);
		});
		m_relations.stream().forEach(relation -> {
			Edge<RDBTable, RDBRelation> edge = new Edge<>(
					vertices.get(relation.getTable1()), 
					vertices.get(relation.getTable2()), 
					relation, 
					true);
			vertices.get(relation.getTable1()).addEdge(edge);
			vertices.get(relation.getTable2()).addEdge(edge);
		});
		return graph;
	}
}
