package migration.core.model.transfer;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import asjava.uniobjects.UniSession;
import migration.core.db.multivalue.impl.uv.UniVerseDatabaseClient;

public class DeleteFiles {
	@Test
	public void perform() throws Exception {
		UniVerseDatabaseClient u2Client = new UniVerseDatabaseClient("localhost", 31438, "u2user", "!u2password");
		UniSession session = u2Client.connect("sakila");
		List<String> files = Arrays.asList("actor", "film", "city", "customer", "category", "address", "film_text");
		try {
			for (String file : files) {
				try {
					session.command("DELETE.FILE " + file).exec();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			u2Client.disconnect(session);
		}
	}
}
