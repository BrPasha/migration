package migration.core.algorithm;

import java.util.Map;

import org.junit.Test;

import migration.core.algorithm.graph.Graph;
import migration.core.algorithm.graph.Vertex;
import migration.core.db.relational.impl.mysql.MySqlDatabaseClient;
import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;

public class GraphOrderingTest {

	@Test
	public void test() throws Exception {
		MySqlDatabaseClient client = new MySqlDatabaseClient("localhost", 3306, "sakila", "mysql", "mysql");
		RDBStructure structure = new RDBStructure(client.getTables(), client.getRelations());
		Graph<RDBTable,RDBRelation> graph = structure.constructGraph();
		Map<Vertex<RDBTable, RDBRelation>, Long> weights = graph.getWeights();
		System.out.println(weights);
	}

}
