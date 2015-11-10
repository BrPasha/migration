package migration.core.algorithm.graph;

public class Edge<T, E> {
	private Vertex<T, E> v1;
	private Vertex<T, E> v2;
	private E data;
	private boolean directed;
	
	public Edge(Vertex<T, E> v1, Vertex<T, E> v2, E data, boolean directed) {
		this.v1 = v1;
		this.v2 = v2;
		this.data = data;
		this.directed = directed;
	}
	
	public Vertex<T, E> getV1() {
		return v1;
	}
	
	public Vertex<T, E> getV2() {
		return v2;
	}
	
	public boolean isDirected() {
		return directed;
	}
	
	public E getData() {
		return data;
	}
	
	public boolean canWalkFrom(Vertex<T, E> v) {
		if (v != v1 && v != v2) {
			return false;
		}
		if (directed) {
			return v == v1;
		}
		return true;
	}

	public Vertex<T, E> walkFrom(Vertex<T, E> vertex) {
		if (directed) {
			return vertex == v1 ? v2 : null;
		}
		if (vertex == v1) {
			return v2;
		} else if (vertex == v2) {
			return v1;
		}
		return null;
	}
	
	public boolean canWalkTo(Vertex<T, E> v) {
		if (v != v1 && v != v2) {
			return false;
		}
		if (directed) {
			return v == v2;
		}
		return true;
	}
	
	public Vertex<T, E> walkTo(Vertex<T, E> vertex) {
		if (directed) {
			return vertex == v2 ? v1 : null;
		}
		if (vertex == v1) {
			return v2;
		} else if (vertex == v2) {
			return v1;
		}
		return null;
	}
}
