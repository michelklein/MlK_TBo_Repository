package de.uni.mannheim.semantic.comparison;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Location;

public class LocationsComparator {

	private static final int MAX_LOCATIONS_COUNT = 5;
	private LocationComparator comparator = new LocationComparator();

	public List<CompareResult> compare(Set<Location> locations1,
			Set<Location> locations2) {
		if (locations1 == null || locations2 == null) {
			return null;
		}

		// get prio locations from celebrity
		Set<Location> prios = new HashSet<Location>();
		for (Location l2 : locations2) {
			if (l2.isPrio()) {
				prios.add(l2);
			}
		}

		List<Location> comparedLocations = new ArrayList<Location>();
		List<CompareResult> results = getCompareResulsts(locations1, prios,
				comparedLocations);

		if (results.size() < MAX_LOCATIONS_COUNT
				&& locations1.size() > results.size()) {
			Set<Location> locationsWithoutPrio = getLocationsWithoutPrio(locations2);
			results.addAll(getCompareResulsts(locations1, locationsWithoutPrio,
					comparedLocations));
		}

		return results;
	}

	private List<CompareResult> getCompareResulsts(Set<Location> list1,
			Set<Location> list2, List<Location> usedLocations) {
		List<CompareResult> results = new ArrayList<CompareResult>();
		for (Location l2 : list2) {
			CompareResult bestResult = null;
			Location bestLocation = null;
			for (Location l1 : list1) {
				// skip already compared locations
				if (usedLocations.contains(l1)) {
					continue;
				}
				CompareResult result = comparator.compare(l1, l2);
				if (bestResult == null) {
					bestResult = result;
					bestLocation = l1;
				}
				if (result.getValue() > bestResult.getValue()) {
					bestResult = result;
					bestLocation = l1;
				}
			}
			usedLocations.add(bestLocation);
			if (bestResult != null) {
				results.add(bestResult);
			}
			if(results.size() >= MAX_LOCATIONS_COUNT) {
				return results;
			}
		}
		return results;
	}

	private Set<Location> getLocationsWithoutPrio(Set<Location> locations) {
		Set<Location> result = new HashSet<Location>();
		for (Location l2 : locations) {
			if (!l2.isPrio()) {
				result.add(l2);
			}
		}
		return result;
	}

}
