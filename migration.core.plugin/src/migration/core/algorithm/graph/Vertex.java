package migration.core.algorithm.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Vertex<T, E> implements Iterable<Vertex<T, E>> {
	private T data;
	private List<Edge<T, E>> edges;
	
	public Vertex(T data) {
		this.data = data;
		edges = new ArrayList<>();
	}

	public T getData() {
		return data;
	}
	
	public void addEdge(Edge<T, E> e) {
		edges.add(e);
	}
	
	public List<Edge<T, E>> getOutgoingEdges() {
		return edges.stream().filter(edge -> edge.canWalkFrom(this)).collect(Collectors.toList());
	}
	
	public List<Edge<T, E>> getIngoingDirectedEdges() {
		return edges.stream().filter(edge -> edge.isDirected() && edge.canWalkTo(this)).collect(Collectors.toList());
	}
	
	public List<Vertex<T, E>> getAccessibleNeighbours() {
		return getOutgoingEdges().stream().map(edge -> edge.walkFrom(this)).collect(Collectors.toList());
	}
	
	@Override
	public Iterator<Vertex<T, E>> iterator() {
		return new DepthFirstVertexIterator<>(this);
	}
	
	public Stream<Vertex<T, E>> stream() {
		return StreamSupport.stream(spliterator(), false);
	}
}
