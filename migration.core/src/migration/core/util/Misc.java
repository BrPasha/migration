package migration.core.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Collections2;

public class Misc {
	private Misc() {
	}
	
	public static <Elem> List<List<Elem>> permutationsFloatingSize(List<Elem> elements) {
		List<List<Elem>> result = new ArrayList<>();
		if (elements.size() > 1) {
			result.addAll(Collections2.permutations(elements));
			for (int i = 0; i < elements.size(); i++) {
				List<Elem> subList = new ArrayList<>(elements);
				subList.remove(i);
				result.addAll(permutationsFloatingSize(subList));
			}
		} else if (elements.size() == 1) {
			result.add(elements);
		}
		return result;
	}
}
