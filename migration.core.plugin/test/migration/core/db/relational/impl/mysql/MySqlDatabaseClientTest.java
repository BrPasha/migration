package migration.core.db.relational.impl.mysql;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.BeforeClass;
import org.junit.Test;

import migration.core.model.mv.MVStructure;
import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;

public class MySqlDatabaseClientTest {

	private static MySqlDatabaseClient client;

	@BeforeClass
	public static void initClient() {
		client = new MySqlDatabaseClient("localhost", 3306, "sakila", "mysql", "mysql");
	}
	
	@Test
	public void testGetTables() throws Exception {
		MySqlDatabaseClient client = new MySqlDatabaseClient("localhost", 3306, "sakila", "mysql", "mysql");
		List<RDBTable> tables = client.getTables();
//		System.out.println(tables);
	}

	@Test
	public void testGetRelations() throws Exception {
		List<RDBRelation> relations = client.getRelations();
//		relations.stream().forEach(rel -> System.out.println(rel));
	}

	@Test
	public void test3() throws Exception {
		List<RDBTable> tables = client.getTables();
		List<RDBRelation> relations = client.getRelations();
		RDBStructure structure = new RDBStructure(tables, relations);
		List<MVStructure> proposedConversions = structure.proposeConversions();
		Set<MVStructure> structures = new HashSet<>(proposedConversions);
		TreeSet<MVStructure> orderedStructures = new TreeSet<>(new Comparator<MVStructure>() {
			@Override
			public int compare(MVStructure o1, MVStructure o2) {
				return o1.weight(structure.getRelations()) - o2.weight(structure.getRelations()) > 0 ? 1 : -1;
			}
		});
		orderedStructures.addAll(structures);
		System.out.println(orderedStructures.size());
		
		orderedStructures.stream().forEach(s -> {
			System.out.println();
			s.getTables().stream().forEach(table -> System.out.println(table.getSourceTables()));
			System.out.println(s.weight(structure.getRelations()));
		});
	}
}
