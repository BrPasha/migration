package migration.core.algorithm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import migration.core.model.rdb.RDBStructure;
import migration.core.model.transfer.Transfer;

public class RDBStructureToGraphTest {

	@Test
	public void graph1() throws Exception {
		RDBStructure structure = new ObjectMapper().readValue(
				RDBStructureToGraphTest.class.getResourceAsStream("graph1.json"), RDBStructure.class);
		List<Set<Transfer>> transformations = Transfer.proposeTransformations(structure);
		Set<Set<Transfer>> structures = new HashSet<>(transformations);
		System.out.println(structures.size());
		
		structures.stream().forEach(s -> {
			System.out.println();
			s.stream().forEach(transfer -> System.out.println(transfer.getBaseTable() + ": " + transfer.getEmbeddedTables()));
			System.out.println(s.stream().mapToDouble(transfer -> transfer.weight(structure.getRelations())).sum());
		});
	}
	
	@Test
	public void graph2() throws Exception {
		RDBStructure structure = new ObjectMapper().readValue(
				RDBStructureToGraphTest.class.getResourceAsStream("graph2.json"), RDBStructure.class);
		List<Set<Transfer>> transformations = Transfer.proposeTransformations(structure);
		Set<Set<Transfer>> structures = new HashSet<>(transformations);
		System.out.println(structures.size());
		
		structures.stream().forEach(s -> {
			System.out.println();
			s.stream().forEach(transfer -> System.out.println(transfer.getBaseTable() + ": " + transfer.getEmbeddedTables()));
			System.out.println(s.stream().mapToDouble(transfer -> transfer.weight(structure.getRelations())).sum());
		});
	}
}
