package migration.core.model;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import migration.core.model.rdb.RDBStructure;

public class RDBStructureTest {

	@Test
	public void parseStructure() throws JsonParseException, JsonMappingException, IOException {
		RDBStructure structure = new ObjectMapper().readValue(
				RDBTableTest.class.getResourceAsStream("RDBStructureTest1.json"), RDBStructure.class);
		System.out.println(structure);
	}
}
