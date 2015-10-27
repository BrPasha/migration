package migration.core.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;

public class RootTablesTest {
	
	@Test
	public void test1() throws JsonParseException, JsonMappingException, IOException {
		RDBStructure structure = new ObjectMapper().readValue(
				getClass().getResourceAsStream("RootTablesTest1.json"), RDBStructure.class);
		List<RDBTable> rootTables = RootTables.defineRootTables(structure);
		assertNotNull(rootTables);
		assertEquals(1, rootTables.size());
		assertEquals("table1", rootTables.get(0).getName());
	}
	
	@Test
	public void test2() throws JsonParseException, JsonMappingException, IOException {
		RDBStructure structure = new ObjectMapper().readValue(
				getClass().getResourceAsStream("RootTablesTest2.json"), RDBStructure.class);
		List<RDBTable> rootTables = RootTables.defineRootTables(structure);
		assertNotNull(rootTables);
		assertEquals(2, rootTables.size());
		assertEquals("table1", rootTables.get(0).getName());
		assertEquals("table3", rootTables.get(1).getName());
	}
}
