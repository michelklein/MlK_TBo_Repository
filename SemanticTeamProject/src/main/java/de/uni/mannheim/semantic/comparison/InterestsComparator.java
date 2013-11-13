package de.uni.mannheim.semantic.comparison;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hp.hpl.jena.sparql.pfunction.library.container;

import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Interest;
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
		if (fbInterests.size() == 0 || celebrityInterests.size() == 0) {
			return new ArrayList<InterestCompareResult>();
		}
		// define the steps per movie
		int steps = 100 / celebrityInterests.size();
		List<InterestCompareResult> resultList = new ArrayList<InterestCompareResult>();
		HashMap<String, List<Interest>> genreToInterests = new HashMap<String, List<Interest>>();

		for (Interest i : fbInterests) {
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
		// if there are more then five genres. use only the genres with the most
		// movies
		while (genreToInterests.size() > 5) {
			genreToInterests.remove(getKeyForSmallesList(genreToInterests));
		}

		// sort genre list
		List<String> sortedGenreList = new ArrayList<String>();
		while (sortedGenreList.size() < 5) {
			String genre = getKeyForGreatestList(genreToInterests,
					sortedGenreList);
			sortedGenreList.add(genre);
		}

		// create one InterestCompareResulsts per genre
		for (String genre : sortedGenreList) {
			// contains all interests for the current genre
			List<Interest> interestPerGenre = new ArrayList<Interest>();
			InterestCompareResult interestCompareResult = new InterestCompareResult(
					0, genre, genreToInterests.get(genre), interestPerGenre);
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
				interestCompareResult.setValue(steps * interestPerGenre.size());
				resultList.add(interestCompareResult);
			}
		}

		return resultList;
	}

	private String getKeyForSmallesList(
			Map<String, List<Interest>> genreToInterest) {
		String key = null;
		int min = 999;
		for (String k : genreToInterest.keySet()) {
			List<Interest> list = genreToInterest.get(k);
			if (list.size() < min) {
				min = list.size();
				key = k;
			}
		}
		return key;
	}

	private String getKeyForGreatestList(
			Map<String, List<Interest>> genreToInterest, List<String> except) {
		String key = null;
		int max = 0;
		for (String k : genreToInterest.keySet()) {
			if (except.contains(k)) {
				continue;
			}
			List<Interest> list = genreToInterest.get(k);
			if (list.size() > max) {
				max = list.size();
				key = k;
			}
		}
		return key;
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
