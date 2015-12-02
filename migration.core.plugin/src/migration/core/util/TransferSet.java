package migration.core.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import migration.core.model.rdb.RDBStructure;
import migration.core.model.transfer.Transfer;

public class TransferSet extends HashSet<Transfer> implements Set<Transfer> {
	private static final long serialVersionUID = 1L;

	private int m_hashCode = -1;
	private double m_weight = -1;
	
	public TransferSet() {
		super();
		m_hashCode = calculateHash();
	}
	
	public TransferSet(Collection<Transfer> elements) {
		super(elements);
		m_hashCode = calculateHash();
	}
	
	public double getWeight(RDBStructure structure) {
		if (m_weight < 0) {
			m_weight = Transfer.getWeight(this, structure);
		}
		return m_weight;
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
		if (m_hashCode == -1) {
			m_hashCode = calculateHash();
		}
		return m_hashCode;
	}
	
	@Override
	public boolean add(Transfer e) {
		boolean res = super.add(e);
		m_hashCode = -1;
		m_weight = -1;
		return res;
	}
	
	@Override
	public boolean addAll(Collection<? extends Transfer> c) {
		boolean res = super.addAll(c);
		m_hashCode = -1;
		m_weight = -1;
		return res;
	}
	
	@Override
	public boolean remove(Object o) {
		boolean res = super.remove(o);
		m_hashCode = -1;
		m_weight = -1;
		return res;
	}
}
