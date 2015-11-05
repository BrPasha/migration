package migration.core.algorithm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import migration.core.model.mv.MVStructure;
import migration.core.model.rdb.RDBStructure;

public class RDBStructureToGraphTest {

	@Test
	public void graph1() throws Exception {
		RDBStructure structure = new ObjectMapper().readValue(
				RDBStructureToGraphTest.class.getResourceAsStream("graph1.json"), RDBStructure.class);
		List<MVStructure> proposedConversions = structure.proposeConversions();
		Set<MVStructure> structures = new HashSet<>(proposedConversions);
//		System.out.println(structures.size());
		
		structures.stream().forEach(s -> {
			System.out.println();
			s.getTables().stream().forEach(table -> System.out.println(table.getSourceTables()));
			System.out.println(s.weight(structure.getRelations()));
		});
	}
	
}
