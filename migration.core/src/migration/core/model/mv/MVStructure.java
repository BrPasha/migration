package migration.core.model.mv;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBRelationType;
import migration.core.model.rdb.RDBTable;

public class MVStructure {
	private Set<MVTable> m_tables;

	public MVStructure(List<MVTable> tables) {
		m_tables = new HashSet<>(tables);
	}

	public Set<MVTable> getTables() {
		return m_tables;
	}
	
	@Override
	public String toString() {
		return m_tables.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_tables == null) ? 0 : m_tables.hashCode());
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
		MVStructure other = (MVStructure) obj;
		if (m_tables == null) {
			if (other.m_tables != null)
				return false;
		} else if (!m_tables.equals(other.m_tables))
			return false;
		return true;
	}
	
	public double weight(List<RDBRelation> relations) {
		Double result = 0.0;
		for (MVTable mvTable : m_tables) {
			List<RDBTable> sourceTables = mvTable.getSourceTables();
			if (sourceTables.size() > 1) {
				final RDBTable baseTable = sourceTables.get(0);
				List<RDBTable> remaining = sourceTables.subList(1, sourceTables.size());
				BiFunction<Double, RDBTable, Double> accumulator = new BiFunction<Double, RDBTable, Double>() {
					@Override
					public Double apply(Double t, RDBTable u) {
						Double result = t;
						for (RDBRelation relation : relations) {
							if ((relation.getTable1().equals(baseTable.getName())
									&& relation.getTable2().equals(u.getName()))) {
								result = result + 1;
							} else if ((relation.getTable2().equals(baseTable.getName())
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
				result = remaining.stream().reduce(result, accumulator, (t, u) -> t + u);
			}
		}
		return result;
	}
}
