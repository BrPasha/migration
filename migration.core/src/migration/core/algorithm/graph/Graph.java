package migration.core.algorithm.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Graph<V, E> {
	private List<Vertex<V, E>> vertices = new ArrayList<>();
	
	public Graph() {
	}
	
	public void addVertex(Vertex<V, E> vertex) {
		vertices.add(vertex);
	}
	
	public Map<Vertex<V, E>, Long> getWeights() {
		return vertices.stream().collect(Collectors.toMap(vertex -> vertex, vertex -> vertex.stream().count()));
	}
}
