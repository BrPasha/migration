package migration.core.db.relational.impl.mysql;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import migration.core.model.rdb.RDBRelation;
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
		relations.stream().forEach(rel -> System.out.println(rel));
	}

}
