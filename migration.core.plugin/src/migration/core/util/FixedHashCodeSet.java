package migration.core.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FixedHashCodeSet<E> extends HashSet<E> implements Set<E> {
	private static final long serialVersionUID = 1L;

	private int m_hashCode = -1;
	
	public FixedHashCodeSet() {
		super();
		m_hashCode = calculateHash();
	}
	
	public FixedHashCodeSet(Collection<E> elements) {
		super(elements);
		m_hashCode = calculateHash();
	}
	
	private int calculateHash() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return m_hashCode;
	}
	
	@Override
	public boolean add(E e) {
		boolean res = super.add(e);
		m_hashCode = calculateHash();
		return res;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean res = super.addAll(c);
		m_hashCode = calculateHash();
		return res;
	}
	
	@Override
	public boolean remove(Object o) {
		boolean res = super.remove(o);
		m_hashCode = calculateHash();
		return res;
	}
}
