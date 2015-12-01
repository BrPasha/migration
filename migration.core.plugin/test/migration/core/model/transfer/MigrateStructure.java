package migration.core.model.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import migration.core.db.multivalue.impl.uv.UniVerseDatabaseClient;
import migration.core.db.relational.impl.mysql.MySqlDatabaseClient;
import migration.core.model.mv.MVFile;
import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;

public class MigrateStructure {

	@Test
	public void perform() throws Exception {
		MySqlDatabaseClient client = new MySqlDatabaseClient("localhost", 3306, "sakila", "mysql", "mysql");
		
		List<RDBTable> tables = client.getTables();
		List<RDBRelation> relations = client.getRelations();
		RDBStructure structure = new RDBStructure(tables, relations);
		
		List<Set<Transfer>> transformations = Transfer.proposeTransformations(structure);
		
		UniVerseDatabaseClient u2Client = new UniVerseDatabaseClient("localhost", 31438, "u2user", "!u2password");
		u2Client.createAccount("TESTACC");
		
		Set<Transfer> transformation1 = transformations.get(0);
		Plan plan = new Plan(new ArrayList<>(transformation1), client);
		while (plan.next()) {
			MVFile mvFile = plan.getStructure();
			u2Client.createFile("TESTACC", mvFile);
			try (Data data = plan.getData()) {
				u2Client.exportData("TESTACC", mvFile.getName(), data);
			}
		}
	}
	
}
