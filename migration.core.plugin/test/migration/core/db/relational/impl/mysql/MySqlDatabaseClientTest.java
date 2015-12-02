package migration.core.db.relational.impl.mysql;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import migration.core.model.mv.MVFile;
import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;
import migration.core.model.transfer.Transfer;
import migration.core.util.TransferSet;

public class MySqlDatabaseClientTest {

	private static MySqlDatabaseClient client;

	@BeforeClass
	public static void initClient() {
		client = new MySqlDatabaseClient("wal-vm-sql2mv", 3306, "sakila", "root", "admin");
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
		List<TransferSet> transformations = Transfer.proposeTransformations(structure);
		System.out.println(transformations.size());
		
		transformations.stream().forEach(s -> {
			System.out.println("------------------------------------------------------------------------");
			s.stream().forEach(transfer -> System.out.println(transfer.getBaseTable() + ": " + transfer.getEmbeddedTables()));
			System.out.println(s.stream().mapToDouble(transfer -> transfer.weight(structure.getRelations())).sum());
			System.out.println();
			s.stream().forEach(tr -> {
				MVFile mvt = tr.constructMVFile();
				System.out.println(mvt.toString() + ": " + mvt.getFields());
			});
		});
	}
}
