package migration.core.model.transfer;

import static migration.core.util.Misc.permutationsFloatingSize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.google.common.collect.Collections2;

import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBRelationType;
import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;
import migration.core.util.Pair;

public class Transfer {
	private RDBTable m_baseTable;
	private Set<RDBTable> m_embeddedTables = new HashSet<>();
	
	public Transfer(RDBTable baseTable, Collection<RDBTable> embeddedTables) {
		m_baseTable = baseTable;
		m_embeddedTables.addAll(embeddedTables);
	}
	
	public Transfer(RDBTable baseTable) {
		m_baseTable = baseTable;
	}
	
	public RDBTable getBaseTable() {
		return m_baseTable;
	}
		
	public Set<RDBTable> getEmbeddedTables() {
		return Collections.unmodifiableSet(m_embeddedTables);
	}
	
	public double weight(List<RDBRelation> relations) {
		Double result = 0.0;
		BiFunction<Double, RDBTable, Double> accumulator = new BiFunction<Double, RDBTable, Double>() {
			@Override
			public Double apply(Double t, RDBTable u) {
				Double result = t;
				for (RDBRelation relation : relations) {
					if ((relation.getTable1().equals(getBaseTable().getName())
							&& relation.getTable2().equals(u.getName()))) {
						result = result + 1;
					} else if ((relation.getTable2().equals(getBaseTable().getName())
							&& relation.getTable1().equals(u.getName()))) {
						if (relation.getRelationType() == RDBRelationType.primaryToForeign) {
							result = result - 0.5;
						} else {
							result = result + 1;
						}
					}
				}
				return result;
			}
		};
		result = getEmbeddedTables().stream().reduce(result, accumulator, (t, u) -> t + u);
		return result;
	}
	
	public static List<Set<Transfer>> proposeTransformations(RDBStructure structure) {
		List<Set<Transfer>> transformations = new ArrayList<>();
		Collection<List<RDBTable>> permutations = Collections2.permutations(structure.getTables());
		int count = 0;
		for (List<RDBTable> tables : permutations) {
			if (count++ > 2) {
				break;
			}
			LinkedList<RDBTable> remainingTables = new LinkedList<>(tables);
			if (!remainingTables.isEmpty()) {
				RDBTable table = remainingTables.pop();
				List<Set<Transfer>> transfers = process(structure, table, remainingTables);
				transformations.addAll(transfers);
			}
		}
		return transformations;
	}
	
	private static List<Set<Transfer>> process(RDBStructure structure, RDBTable table, LinkedList<RDBTable> tables) {
		List<RDBTable> related = findRelated(table, tables, structure.getRelations());
		List<Set<Transfer>> result = new ArrayList<>();
		if (related.isEmpty()) {
			Transfer transfer = new Transfer(table);
			if (!tables.isEmpty()) {
				LinkedList<RDBTable> remaining = new LinkedList<RDBTable>(tables);
				List<Set<Transfer>> nextTransfers = process(structure, remaining.pop(), remaining);
				nextTransfers.stream().forEach(list -> list.add(transfer));
				result.addAll(nextTransfers);
			} else {
				Set<Transfer> nextTransfers = new HashSet<>();
				nextTransfers.add(transfer);
				result.add(nextTransfers);
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
				List<Set<Transfer>> res = new ArrayList<>();
				Transfer transfer = new Transfer(table, p.first());
				if (!p.second().isEmpty()) {
					List<Set<Transfer>> nextTransfers = process(structure, p.second().pop(), p.second());
					nextTransfers.stream().forEach(list -> list.add(transfer));
					res.addAll(nextTransfers);
				} else {
					Set<Transfer> nextTransfers = new HashSet<>();
					nextTransfers.add(transfer);
					res.add(nextTransfers);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_baseTable == null) ? 0 : m_baseTable.hashCode());
		result = prime * result + ((m_embeddedTables == null) ? 0 : m_embeddedTables.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transfer other = (Transfer) obj;
		if (m_baseTable == null) {
			if (other.m_baseTable != null)
				return false;
		} else if (!m_baseTable.equals(other.m_baseTable))
			return false;
		if (m_embeddedTables == null) {
			if (other.m_embeddedTables != null)
				return false;
		} else if (!m_embeddedTables.equals(other.m_embeddedTables))
			return false;
		return true;
	}
}
