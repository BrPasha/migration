package migration.core.model.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import migration.core.db.multivalue.impl.uv.UniVerseDatabaseClient;
import migration.core.db.relational.impl.mysql.MySqlDatabaseClient;
import migration.core.model.mv.MVFile;
import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBRelationType;
import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;
import migration.core.util.TransferSet;

public class MigrateStructure {

	@Test
	public void perform() throws Exception {
		MySqlDatabaseClient client = new MySqlDatabaseClient("localhost", 3306, "sakila", "mysql", "mysql");
		
		List<RDBTable> tables = client.getTables();
		List<RDBRelation> relations = client.getRelations();
		relations.add(new RDBRelation("film", "film_id", "film_text", "film_id", RDBRelationType.oneToOne));
		RDBStructure structure = new RDBStructure(tables, relations);
		
		List<TransferSet> transformations = Transfer.proposeTransformations(structure);
		System.out.println(transformations.get(0).getWeight(structure));
		UniVerseDatabaseClient u2Client = new UniVerseDatabaseClient("localhost", 31438, "u2user", "!u2password");
		u2Client.createAccount("TESTACC");
		
		Set<Transfer> transformation1 = transformations.get(0);
		transformation1.stream().forEach(tr -> System.out.println(tr.getBaseTable() + ": " + tr.getEmbeddedTables()));
		Plan plan = new Plan(new ArrayList<>(transformation1), client);
		while (plan.next()) {
			MVFile mvFile = plan.getStructure();
			u2Client.createFile("TESTACC", mvFile);
			try (Data data = plan.getData()) {
				u2Client.exportData("TESTACC", mvFile.getName(), mvFile, data);
			}
		}
	}
	
}
