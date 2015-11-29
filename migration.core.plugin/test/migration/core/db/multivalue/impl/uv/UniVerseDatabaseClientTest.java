package migration.core.db.multivalue.impl.uv;

import java.util.Arrays;

import org.junit.Test;

import migration.core.model.mv.MVField;
import migration.core.model.mv.MVFile;

public class UniVerseDatabaseClientTest {

	@Test
	public void testCreateAccount() throws Exception {
		UniVerseDatabaseClient client = new UniVerseDatabaseClient("localhost", 31438, "u2user", "!u2password");
//		try {
//			client.deleteAccount("TESTACC");
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
		client.createAccount("TESTACC");
		MVField field1 = new MVField("FIELD1", "D", "1", "", "", "Field 1", "10L", "S", "");
		MVField field2 = new MVField("FIELD2", "D", "2", "", "", "Field 2", "20L", "S", "");
		MVField field3 = new MVField("FIELD3", "D", "3", "", "", "Field 3", "30L", "S", "");
		client.createFile("TESTACC", new MVFile("TESTFILE", Arrays.asList(field1, field2, field3)));
		
		System.out.println();
	}

}
