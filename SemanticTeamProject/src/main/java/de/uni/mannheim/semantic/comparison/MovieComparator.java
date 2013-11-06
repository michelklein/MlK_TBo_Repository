package de.uni.mannheim.semantic.comparison;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Interest;

public class MovieComparator extends AbstractComparator<String> {

	@Override
	public CompareResult compare(String o1, String o2) {
		// TODO Auto-generated method stub

		result = new CompareResult(0, "Overall", o1, o2);
		result.getSubresults().add(new CompareResult(10, "Zehn", o1, o2));
		result.getSubresults().add(new CompareResult(20, "Zwanzig", o1, o2));
		result.setValue(30);

		return result;
	}

	public List<CompareResult> compareList(List<Interest> fbList,
			List<Interest> cList) {
		// TODO Auto-generated method stub

		List<CompareResult> resultList = new ArrayList<CompareResult>();
		Set<String> genreList = new HashSet<String>();
		for (Interest i : fbList) {
			genreList.addAll(i.getGenre());
		}

		for (String g : genreList) {
			CompareResult cr = new CompareResult(0, g, g, g);
			resultList.add(cr);
			for (Interest i : cList) {
				if (i.getGenre().contains(g)) {
					CompareResult sr = new CompareResult(100 / cList.size(),
							i.getName(), o1, o2);

					cr.getSubresults().add(sr);
				}
			}
		}

		return resultList;
	}
}
