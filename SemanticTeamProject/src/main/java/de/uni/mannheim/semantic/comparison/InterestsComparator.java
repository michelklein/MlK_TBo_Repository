package de.uni.mannheim.semantic.comparison;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Interest;
import de.uni.mannheim.semantic.model.InterestCompareObject;
import de.uni.mannheim.semantic.model.InterestCompareResult;

/**
 * Compare the genre of interests
 * 
 * @author michelklein
 */
public class InterestsComparator {

	public List<InterestCompareResult> compare(List<Interest> interests1,
			List<Interest> interests2) {
		List<Interest> fbInterests = filterInterestsWithCover(interests1);
		List<Interest> celebrityInterests = filterInterestsWithCover(interests2);
		if(fbInterests.size() == 0 || celebrityInterests.size() == 0) {
			return null;
		}
		// define the steps per movie
		int steps = 100 / celebrityInterests.size();
		List<InterestCompareResult> resultList = new ArrayList<InterestCompareResult>();
		Set<String> genreList = new HashSet<String>();
		HashMap<String, List<Interest>> genreToInterests = new HashMap<String, List<Interest>>();

		for (Interest i : fbInterests) {
			// read all genres from the first interest list and put them into a
			// set
			genreList.addAll(i.getGenre());
			// iterate over all genre for one interest
			for (String genre : i.getGenre()) {
				// map these interest to the genre
				List<Interest> interestList = genreToInterests.get(genre);
				// check if there is an existing interest list for genre and add
				// interest
				if (interestList != null) {
					if (!interestList.contains(i)) {
						interestList.add(i);
					}
					// otherwise create new interest list
				} else {
					interestList = new ArrayList<Interest>();
					interestList.add(i);
					genreToInterests.put(genre, interestList);
				}
			}
		}

		// create one InterestCompareResulsts per genre
		for (String genre : genreList) {
			// contains all interests for the current genre
			List<Interest> interestPerGenre = new ArrayList<Interest>();
			InterestCompareResult interestCompareResult = new InterestCompareResult(
					steps * interestPerGenre.size(), genre,
					genreToInterests.get(genre), interestPerGenre);
			// add the celebrity interests to the list
			for (Interest i : celebrityInterests) {
				if (i.getGenre().contains(genre.trim())) {
					interestPerGenre.add(i);
					interestCompareResult.getSubresults()
							.add(new CompareResult(steps, i.getName(), genre,
									genre));
				}
			}
			// if there is at least one interest per genre, create one
			// InterstCompareResult
			if (interestPerGenre.size() > 0) {
				resultList.add(interestCompareResult);
			}
		}

		// for (String genre : genreList) {
		// InterestCompareObject ico1 = new InterestCompareObject(genre);
		// InterestCompareResult cr = new InterestCompareResult(0, genre,
		// ico1, ico1);
		// resultList.add(cr);
		// for (Interest i : celebrityInterests) {
		// if (i.getGenre().contains(genre.trim())) {
		// ico1.getPicURLs1().add(i.getCoverURL());
		// ico1.getPicURLs2().add(i.getCoverURL());
		// InterestCompareResult sr = new InterestCompareResult(
		// 100 / celebrityInterests.size(), genre, ico1, ico1);
		// cr.getSubresults().add(sr);
		// }
		// }
		// }
		return resultList;
	}

	private List<Interest> filterInterestsWithCover(List<Interest> interests) {
		List<Interest> result = new ArrayList<Interest>();
		for (Interest i : interests) {
			if (i.getCoverURL() != null) {
				result.add(i);
			}
		}

		return result;
	}
}
