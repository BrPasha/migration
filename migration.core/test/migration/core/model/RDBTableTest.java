package migration.core.model;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import migration.core.model.rdb.RDBTable;

public class RDBTableTest {
	
	private InputStream getTestResource(String path)
	{
		return RDBTableTest.class.getResourceAsStream(path);
	}
	
	@Test
	public void jsonParsing() throws JsonParseException, JsonMappingException, IOException {
		RDBTable relationalTable = new ObjectMapper().readValue(
				getTestResource("RDBTableTest1.json"), RDBTable.class);
		System.out.println(relationalTable);
	}
}
