package migration.core.model.rdb;

import static migration.core.util.Misc.permutationsFloatingSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Collections2;

import migration.core.algorithm.graph.Edge;
import migration.core.algorithm.graph.Graph;
import migration.core.algorithm.graph.Vertex;
import migration.core.model.mv.MVStructure;
import migration.core.model.mv.MVTable;
import migration.core.util.Pair;

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
	
	public List<MVStructure> proposeConversions() {
		List<MVStructure> structures = new ArrayList<>();
		for (List<RDBTable> tables : Collections2.permutations(m_tables)) {
			LinkedList<RDBTable> remainingTables = new LinkedList<>(tables);
			if (!remainingTables.isEmpty()) {
				RDBTable table = remainingTables.pop();
				List<List<MVTable>> mvTables = process(table, remainingTables);
				List<MVStructure> permutationStructures = mvTables.stream()
						.map(list -> new MVStructure(list))
						.collect(Collectors.toList());
				structures.addAll(permutationStructures);
			}
		}
		return structures;
	}
	
	private List<List<MVTable>> process(RDBTable table, LinkedList<RDBTable> tables) {
		List<RDBTable> related = findRelated(table, tables, getRelations());
		List<List<MVTable>> result = new ArrayList<>();
		if (related.isEmpty()) {
			MVTable mvTable = new MVTable(table);
			if (!tables.isEmpty()) {
				LinkedList<RDBTable> remaining = new LinkedList<RDBTable>(tables);
				List<List<MVTable>> nextMvTables = process(remaining.pop(), remaining);
				nextMvTables.stream().forEach(list -> list.add(0, mvTable));
				result.addAll(nextMvTables);
			} else {
				List<MVTable> nextMvTables = new ArrayList<>();
				nextMvTables.add(mvTable);
				result.add(nextMvTables);
			}
		} else {
			List<List<RDBTable>> permutationsForRelated = permutationsFloatingSize(related);
			List<Pair<List<RDBTable>, LinkedList<RDBTable>>> usedAndRemaining = permutationsForRelated.stream()
					.map(permutation -> {
						LinkedList<RDBTable> remaining = new LinkedList<RDBTable>(tables);
						remaining.removeAll(permutation);
						return Pair.pair(permutation, remaining);
					})
					.collect(Collectors.toList());
			result.addAll(usedAndRemaining.stream().map(p -> {
				List<List<MVTable>> res = new ArrayList<>();
				MVTable mvTable = new MVTable(table, p.first());
				if (!p.second().isEmpty()) {
					List<List<MVTable>> nextMvTables = process(p.second().pop(), p.second());
					nextMvTables.stream().forEach(list -> list.add(0, mvTable));
					res.addAll(nextMvTables);
				} else {
					List<MVTable> nextMvTables = new ArrayList<>();
					nextMvTables.add(mvTable);
					res.add(nextMvTables);
				}
				return res;
			}).flatMap(list -> list.stream()).collect(Collectors.toList()));
		}
		return result;
	}
	
	private static List<RDBTable> findRelated(RDBTable table, List<RDBTable> tables, List<RDBRelation> relations) {
		return relations.stream()
				.filter(r -> r.getTable1().equals(table.getName()) || r.getTable2().equals(table.getName()))
				.map(r -> r.getTable1().equals(table.getName()) ? r.getTable2() : r.getTable1())
				.map(name -> find(name, tables))
				.filter(t -> t != null)
				.collect(Collectors.toList());
	}
	
	private static RDBTable find(String name, List<RDBTable> tables) {
		Optional<RDBTable> optional = tables.stream().filter(table -> table.getName().equals(name)).findFirst();
		return optional.isPresent() ? optional.get() : null;
	}
}
