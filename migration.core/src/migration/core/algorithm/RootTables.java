package migration.core.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;

public class RootTables {
	
	public static List<RDBTable> defineRootTables(RDBStructure structure) {
		List<RDBTable> result = new ArrayList<>();
		List<RDBTable> tablesWithoutForeignKeys = structure.getTables()
			.stream()
			.filter(
					t -> structure.getRelations().stream().allMatch(r -> !r.getTableForeign().equals(t.getName())))
			.collect(Collectors.toList());
		result.addAll(tablesWithoutForeignKeys);
		return result;
	}
	
}
