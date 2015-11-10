package migration.core.algorithm.graph;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class DepthFirstVertexIterator<T, E> implements Iterator<Vertex<T, E>> {

	private Deque<Iterator<Vertex<T, E>>> stack = new LinkedList<>();
	private Vertex<T, E> next;
	
	public DepthFirstVertexIterator(Vertex<T, E> root) {
		this.next = root;
		stack.push(root.getAccessibleNeighbours().iterator());
	}
	
	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public Vertex<T, E> next() {
		if (next == null) {
			throw new NoSuchElementException();
		}
		Vertex<T, E> result = next;
		advance();
		return result;
	}

	private void advance() {
		Iterator<Vertex<T, E>> neighbours = stack.peek();
		while (!neighbours.hasNext()) {
            stack.pop();
            if (stack.isEmpty()) {
                next = null;
                return;
            }
            neighbours = stack.peek();
        }
        next = neighbours.next();
        stack.push(next.getAccessibleNeighbours().iterator());
	}

}
