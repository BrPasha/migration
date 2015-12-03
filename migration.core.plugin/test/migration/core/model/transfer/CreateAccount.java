package migration.core.model.transfer;

import org.junit.Test;

import migration.core.db.multivalue.impl.uv.UniVerseDatabaseClient;

public class CreateAccount {
	@Test
	public void perform() throws Exception {
		UniVerseDatabaseClient u2Client = new UniVerseDatabaseClient("localhost", 31438, "u2user", "!u2password");
		u2Client.createAccount("sakila");
	}
}
