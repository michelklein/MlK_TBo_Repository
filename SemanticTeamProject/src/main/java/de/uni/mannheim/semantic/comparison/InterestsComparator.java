package de.uni.mannheim.semantic.comparison;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Interest;
import de.uni.mannheim.semantic.model.InterestCompareResult;

public class InterestsComparator {

	public List<CompareResult> compare(List<Interest> interests1,
			List<Interest> interests2) {
		List<CompareResult> resultList = new ArrayList<CompareResult>();
		Set<String> genreList = new HashSet<String>();
		for (Interest i : interests1) {
			genreList.addAll(i.getGenre());
		}

		for (String g : genreList) {
			CompareResult cr = new CompareResult(0, g, g, g);
			resultList.add(cr);
			for (Interest i : interests2) {
				if (i.getGenre().contains(g)) {
					CompareResult sr = new InterestCompareResult(
							100 / interests2.size(), i.getName(), i, i);
					cr.getSubresults().add(sr);
				}
			}
		}
		return resultList;
	}
}
