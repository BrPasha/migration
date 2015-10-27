package migration.core.algorithm;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import migration.core.model.rdb.RDBStructure;

public class RDBTablesGraphTest {
	@Test
	public void test1() throws JsonParseException, JsonMappingException, IOException {
		RDBStructure structure = new ObjectMapper().readValue(
				getClass().getResourceAsStream("RDBTablesGraphTest1.json"), RDBStructure.class);
		RDBTablesGraph graph = new RDBTablesGraph(structure);
		System.out.println(Arrays.deepToString(graph.getAdjacency()));
	}
	
	@Test
	public void test2() throws JsonParseException, JsonMappingException, IOException {
		RDBStructure structure = new ObjectMapper().readValue(
				getClass().getResourceAsStream("RDBTablesGraphTest2.json"), RDBStructure.class);
		RDBTablesGraph graph = new RDBTablesGraph(structure);
		System.out.println(Arrays.deepToString(graph.getAdjacency()));
	}
}
