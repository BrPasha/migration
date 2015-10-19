package migration.core.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;

public class RDBTablesGraph {
	private RDBStructure m_structure;
	private int[][][] m_adjacency;
	
	private Map<String, RDBTable> m_mapTables;
	private Map<String, Integer> m_mapTablesIndices;
	
	public RDBTablesGraph(RDBStructure structure) {
		m_structure = structure;
		m_mapTables = getTablesMap();
		m_mapTablesIndices = getTablesIndices();
		m_adjacency = evaluateAdjacency();
	}

	private int[][][] evaluateAdjacency() {
		int[][][] adjMatrix = initializeAdjacencyMatrix();
		for (RDBRelation relation : m_structure.getRelations()) {
			String tablePrimary = relation.getTablePrimary();
			int idxPrimary = m_mapTablesIndices.get(tablePrimary);
			String tableForeign = relation.getTableForeign();
			int idxForeign = m_mapTablesIndices.get(tableForeign);
			adjMatrix[idxPrimary][idxForeign] = append(adjMatrix[idxPrimary][idxForeign], 1);
			adjMatrix[idxForeign][idxPrimary] = append(adjMatrix[idxForeign][idxPrimary], -1);
		}
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix[i].length; j++) {
				if (adjMatrix[i][j] == null) {
					adjMatrix[i][j] = new int[1];
					adjMatrix[i][j][0] = 0;
				}
			}
		}
		return adjMatrix;
	}
	
	private int[] append(int[] array, int value) {
		int[] result;
		if (array == null) {
			result = new int[1];
			result[0] = value;
		} else {
			result = Arrays.copyOf(array, array.length + 1);
			result[array.length] = value;
		}
		return result;
	}

	private int[][][] initializeAdjacencyMatrix() {
		int tablesCount = m_structure.getTables().size();
		int[][][] adjMatrix = new int[tablesCount][][];
		for (int i = 0; i < adjMatrix.length; i++) {
			adjMatrix[i] = new int[tablesCount][];
		}
		return adjMatrix;
	}

	private Map<String, RDBTable> getTablesMap() {
		return m_structure.getTables().stream().collect(
				Collectors.toMap(
						table -> table.getName(), 
						table -> table));
	}
	
	private Map<String, Integer> getTablesIndices() {
		Map<String, Integer> result = new HashMap<>();
		List<RDBTable> tables = m_structure.getTables();
		for (int i = 0; i < tables.size(); i++) {
			result.put(tables.get(i).getName(), i);
		}
		return result;
	}
	
	public int[][][] getAdjacency() {
		return m_adjacency;
	}
}
