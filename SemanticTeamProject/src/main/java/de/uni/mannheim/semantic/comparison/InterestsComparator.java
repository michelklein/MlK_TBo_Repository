package de.uni.mannheim.semantic.comparison;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Interest;
import de.uni.mannheim.semantic.model.InterestCompareObject;
import de.uni.mannheim.semantic.model.InterestCompareResult;

public class InterestsComparator {

	public List<InterestCompareResult> compare(List<Interest> interests1,
			List<Interest> interests2) {
		List<InterestCompareResult> resultList = new ArrayList<InterestCompareResult>();
		Set<String> genreList = new HashSet<String>();
		for (Interest i : interests1) {
			genreList.addAll(i.getGenre());
		}

		for (String g : genreList) {
			InterestCompareObject ico1 = new InterestCompareObject(g);
			InterestCompareResult cr = new InterestCompareResult(0, g, ico1,
					ico1);
			resultList.add(cr);
			for (Interest i : interests2) {
				if (i.getGenre().contains(g.trim())) {
					ico1.getPicURLs1().add(i.getCoverURL());
					ico1.getPicURLs2().add(i.getCoverURL());
					InterestCompareResult sr = new InterestCompareResult(
							100 / interests2.size(), g, ico1, ico1);
					cr.getSubresults().add(sr);
				}
			}
		}
		return resultList;
	}
}
