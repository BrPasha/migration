package migration.core.db.multivalue.impl.uv;

import org.junit.Test;

public class UniVerseDatabaseClientTest {

	@Test
	public void testCreateAccount() throws Exception {
		UniVerseDatabaseClient client = new UniVerseDatabaseClient("localhost", 31438, "UV", "u2user", "!u2password");
		client.createAccount("TTT");
		client.deleteAccount("TTT");
		System.out.println();
	}

}
