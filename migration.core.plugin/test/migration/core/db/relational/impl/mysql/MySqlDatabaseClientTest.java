package migration.core.db.relational.impl.mysql;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.BeforeClass;
import org.junit.Test;

import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;
import migration.core.model.transfer.Transfer;

public class MySqlDatabaseClientTest {

	private static MySqlDatabaseClient client;

	@BeforeClass
	public static void initClient() {
		client = new MySqlDatabaseClient("localhost", 3306, "sakila", "mysql", "mysql");
	}
	
//	@Test
//	public void testGetTables() throws Exception {
//		MySqlDatabaseClient client = new MySqlDatabaseClient("localhost", 3306, "sakila", "mysql", "mysql");
//		List<RDBTable> tables = client.getTables();
////		System.out.println(tables);
//	}
//
//	@Test
//	public void testGetRelations() throws Exception {
//		List<RDBRelation> relations = client.getRelations();
////		relations.stream().forEach(rel -> System.out.println(rel));
//	}
//
	@Test
	public void test3() throws Exception {
		List<RDBTable> tables = client.getTables();
		List<RDBRelation> relations = client.getRelations();
		RDBStructure structure = new RDBStructure(tables, relations);
		List<Set<Transfer>> transformations = Transfer.proposeTransformations(structure);
		Set<Set<Transfer>> structures = new HashSet<>(transformations);
		TreeSet<Set<Transfer>> orderedStructures = new TreeSet<>(new Comparator<Set<Transfer>>() {
			@Override
			public int compare(Set<Transfer> o1, Set<Transfer> o2) {
				return getWeight(o1, structure) - getWeight(o2, structure) > 0 ? 1 : -1;
			}
		});
		orderedStructures.addAll(structures);
		System.out.println(orderedStructures.size());
		
		orderedStructures.stream().forEach(s -> {
			System.out.println();
			s.stream().forEach(transfer -> System.out.println(transfer.getBaseTable() + ": " + transfer.getEmbeddedTables()));
			System.out.println(s.stream().mapToDouble(transfer -> transfer.weight(structure.getRelations())).sum());
		});
	}
	
	private double getWeight(Set<Transfer> transfers, RDBStructure structure) {
		return transfers.stream().mapToDouble(transfer -> transfer.weight(structure.getRelations())).sum();
	}
}
