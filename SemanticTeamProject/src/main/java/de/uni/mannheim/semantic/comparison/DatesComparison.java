package de.uni.mannheim.semantic.comparison;

import java.util.ArrayList;
import java.util.List;

import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.DateObject;

public class DatesComparison {

	private static final int MAX_LOCATIONS_COUNT = 5;
	private DateComparator comparator = new DateComparator();

	public List<CompareResult> compare(List<DateObject> dates1,
			List<DateObject> dates2) {
		if (dates1 == null || dates2 == null) {
			return null;
		}
		// get prio DateObjects from celebrity
		List<DateObject> prios = new ArrayList<DateObject>();
		for (DateObject l2 : dates2) {
			if (l2.isPrio()) {
				prios.add(l2);
			}
		}

		List<DateObject> comparedDateObjects = new ArrayList<DateObject>();
		List<CompareResult> results = getCompareResulsts(dates1, prios,
				comparedDateObjects);

		if (results.size() < MAX_LOCATIONS_COUNT
				&& dates1.size() > results.size()) {
			List<DateObject> dateObjectsWithoutPrio = getDateObjectsWithoutPrio(dates2);
			results.addAll(getCompareResulsts(dates1, dateObjectsWithoutPrio,
					comparedDateObjects));
		}

		return results;
	}

	private List<CompareResult> getCompareResulsts(List<DateObject> list1,
			List<DateObject> list2, List<DateObject> usedDateObjects) {
		List<CompareResult> results = new ArrayList<CompareResult>();
		for (DateObject l2 : list2) {
			CompareResult bestResult = null;
			DateObject bestDateObject = null;
			for (DateObject l1 : list1) {
				// skip already compared DateObjects
				if (usedDateObjects.contains(l1)) {
					continue;
				}
				CompareResult result = comparator.compare(l1, l2);
				if (bestResult == null) {
					bestResult = result;
					bestDateObject = l1;
				}
				if (result.getValue() > bestResult.getValue()) {
					bestResult = result;
					bestDateObject = l1;
				}
			}
			usedDateObjects.add(bestDateObject);
			if (bestResult != null) {
				results.add(bestResult);
			}
			if (results.size() >= MAX_LOCATIONS_COUNT) {
				return results;
			}
		}
		return results;
	}

	private List<DateObject> getDateObjectsWithoutPrio(
			List<DateObject> dateObjects) {
		List<DateObject> result = new ArrayList<DateObject>();
		for (DateObject l2 : dateObjects) {
			if (!l2.isPrio()) {
				result.add(l2);
			}
		}
		return result;
	}
}
