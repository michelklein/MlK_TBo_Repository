package de.uni.mannheim.semantic.comparison;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Location;

public class LocationsComparator {

	private LocationComparator comparator = new LocationComparator();

	public List<CompareResult> compare(Set<Location> locations1,
			Set<Location> locations2) {
		if (locations1 == null || locations2 == null) {
			return null;
		}
		// calculate size of result list
		int size = locations1.size();
		if (size > locations2.size()) {
			size = locations2.size();
		}

		List<CompareResult> results = new ArrayList<CompareResult>();
		List<Location> comparedLocations = new ArrayList<Location>();

		for (Location l2 : locations2) {
			CompareResult bestResult = null;
			Location bestLocation = null;
			for (Location l1 : locations1) {
				// skip already compared locations
				if (comparedLocations.contains(l1)) {
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
			comparedLocations.add(bestLocation);
			if (bestResult != null)
				results.add(bestResult);
		}

		return results;

	}

}
