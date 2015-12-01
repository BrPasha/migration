package migration.core.model.transfer;

import static migration.core.util.Misc.permutationsFloatingSize;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.google.common.collect.Collections2;

import migration.core.model.mv.MVColumnDepth;
import migration.core.model.mv.MVField;
import migration.core.model.mv.MVFile;
import migration.core.model.rdb.RDBColumn;
import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBRelationType;
import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;
import migration.core.util.Pair;

public class Transfer {
	private int m_hashCode;
	
	private RDBTable m_baseTable;
	private TreeSet<RDBTable> m_embeddedTables;

	private RDBStructure m_structure;
	
	public Transfer(RDBTable baseTable, Collection<RDBTable> embeddedTables, RDBStructure structure) {
		m_baseTable = baseTable;
		m_structure = structure;
		m_embeddedTables = new TreeSet<>((table1, table2) -> table1.getName().compareTo(table2.getName()));
		m_embeddedTables.addAll(embeddedTables);
		m_hashCode = calculateHashCode();
	}
	
	public Transfer(RDBTable baseTable, RDBStructure structure) {
		this(baseTable, Collections.emptySet(), structure);
	}
	
	public RDBTable getBaseTable() {
		return m_baseTable;
	}
	
	public RDBStructure getStructure() {
		return m_structure;
	}
		
	public SortedSet<RDBTable> getEmbeddedTables() {
		return Collections.unmodifiableSortedSet(m_embeddedTables);
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
		Set<Set<Transfer>> structures = new HashSet<>(transformations);
		TreeSet<Set<Transfer>> orderedStructures = new TreeSet<>((o1, o2) -> getWeight(o1, structure) - getWeight(o2, structure) > 0 ? -1 : 1);
		orderedStructures.addAll(structures);
		return new ArrayList<>(orderedStructures);
	}
	
	private static double getWeight(Set<Transfer> transfers, RDBStructure structure) {
		return transfers.stream().mapToDouble(transfer -> transfer.weight(structure.getRelations())).sum();
	}
	
	private static List<Set<Transfer>> process(RDBStructure structure, RDBTable table, LinkedList<RDBTable> tables) {
		List<RDBTable> related = findRelated(table, tables, structure.getRelations());
		List<Set<Transfer>> result = new ArrayList<>();
		if (related.isEmpty()) {
			Transfer transfer = new Transfer(table, structure);
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
				Transfer transfer = new Transfer(table, p.first(), structure);
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
		return m_hashCode;
	}

	private int calculateHashCode() {
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
	
	public MVFile constructMVFile() {
		List<MVField> mvFields = new ArrayList<>();
		int fieldLocation = 1;
		Set<String> usedColumnNames = new HashSet<>();
		for (RDBColumn column : m_baseTable.getColumns()) {
			String convCode = resolveConvCode(column);
			String format = resolveFormat(column);
			MVField mvField = new MVField(
					constructUniqueName(usedColumnNames, column), 
					"D", 
					String.valueOf(fieldLocation++), 
					"", 
					convCode, 
					column.getName(), 
					format, 
					MVColumnDepth.singlevalue.value(), 
					"");
			mvFields.add(mvField);
		}
		for (RDBTable embeddedTable : m_embeddedTables) {
			MVColumnDepth depth = resolveDepth(embeddedTable);
			String associationName = depth == MVColumnDepth.multivalue ? embeddedTable.getName() + "_association" : "";
			List<String> addedColumnNames = new ArrayList<>();
			for (RDBColumn column : embeddedTable.getColumns()) {
				if (needToInclude(column)) {
					String convCode = resolveConvCode(column);
					String format = resolveFormat(column);
					String columnUniqueName = constructUniqueName(usedColumnNames, column);
					MVField mvField = new MVField(
							columnUniqueName, 
							"D", 
							String.valueOf(fieldLocation++), 
							"", 
							convCode, 
							column.getName(), 
							format, 
							depth.value(), 
							associationName);
					mvFields.add(mvField);
					addedColumnNames.add(columnUniqueName);
				}
			}
			if (depth == MVColumnDepth.multivalue) {
				MVField mvField = new MVField(
						associationName, 
						"PH", 
						"", 
						String.join(" ", addedColumnNames), 
						"", 
						"", 
						"", 
						"", 
						"");
				mvFields.add(mvField);
			}
		}
		return new MVFile(m_baseTable.getName(), mvFields);
	}

	private String constructUniqueName(Set<String> usedColumnNames, RDBColumn column) {
		String columnName = column.getName();
		int idx = 1;
		while (usedColumnNames.contains(columnName)) {
			columnName = column.getName() + idx++;
		}
		usedColumnNames.add(columnName);
		return columnName;
	}
	
	private MVColumnDepth resolveDepth(RDBTable embeddedTable) {
		RDBRelation relation = findRelation(m_structure.getRelations(), m_baseTable, embeddedTable);
		MVColumnDepth result = MVColumnDepth.singlevalue;
		if (relation.getRelationType() == RDBRelationType.primaryToForeign
			&& relation.getTable2().equals(embeddedTable.getName())) {
			result = MVColumnDepth.multivalue;
		}
		return result;
	}

	static RDBRelation findRelation(List<RDBRelation> relations, RDBTable table1, RDBTable table2) {
		Optional<RDBRelation> optRelation = relations.stream()
				.filter(relation -> (relation.getTable1().equals(table2.getName())
						&& relation.getTable2().equals(table1.getName()))
						|| (relation.getTable1().equals(table1.getName())
							&& relation.getTable2().equals(table2.getName())))
				.findAny();
		RDBRelation relation = optRelation.get();
		return relation;
	}

	private boolean needToInclude(RDBColumn column) {
		return true;
	}

	private String resolveFormat(RDBColumn column) {
		String format = "";
		switch (column.getDataType()) {
		case Types.VARCHAR:
		case Types.NVARCHAR:
		case Types.LONGVARCHAR:
		case Types.CHAR:
		case Types.CLOB:
			format = String.valueOf(column.getColumnSize()) + "L";
			break;
		case Types.TIME:
		case Types.DATE:
		case Types.TIMESTAMP:
		case Types.TIME_WITH_TIMEZONE:
		case Types.TIMESTAMP_WITH_TIMEZONE:
			format = "";
			break;
		case Types.DECIMAL:
		case Types.DOUBLE:
		case Types.INTEGER:
		case Types.NUMERIC:
		case Types.REAL:
		case Types.FLOAT:
		case Types.SMALLINT:
		case Types.TINYINT:
		case Types.BIT:
			format = String.valueOf(column.getColumnSize()) + "R";
			break;
		}
		return format;
	}

	private String resolveConvCode(RDBColumn column) {
		String convCode = "";
		switch (column.getDataType()) {
		case Types.VARCHAR:
		case Types.NVARCHAR:
		case Types.LONGVARCHAR:
		case Types.CHAR:
		case Types.CLOB:
			convCode = "";
			break;
		case Types.TIME:
			convCode = "MTHS";
			break;
		case Types.DATE:
			convCode = "D4/";
			break;
		case Types.TIMESTAMP:
		case Types.TIME_WITH_TIMEZONE:
		case Types.TIMESTAMP_WITH_TIMEZONE:
			convCode = "";
			break;
		case Types.DECIMAL:
		case Types.NUMERIC:
			convCode = "MD" + column.getDecimalDigits();
			break;
		case Types.DOUBLE:
		case Types.REAL:
		case Types.FLOAT:
			convCode = "MR" + column.getDecimalDigits();
			break;
		case Types.INTEGER:
		case Types.SMALLINT:
		case Types.TINYINT:
		case Types.BIT:
			convCode = "MD0";
			break;
		}
		return convCode;
	}
}
