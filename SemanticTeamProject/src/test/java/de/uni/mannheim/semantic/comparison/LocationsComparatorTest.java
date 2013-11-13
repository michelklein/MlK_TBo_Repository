package de.uni.mannheim.semantic.comparison;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.uni.mannheim.semantic.FetchGeoData;
import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Location;

public class LocationsComparatorTest {

	private LocationsComparator comparator = new LocationsComparator();
	private FetchGeoData geoFetcher = new FetchGeoData();
	private String description = "Test-Location";

	@Test
	public void testCompare() {
		Set<Location> locations1 = new HashSet<Location>();
		locations1.add(geoFetcher.getLocation("Hennef", description));
		locations1.add(geoFetcher.getLocation("Bensberg", description));
		locations1.add(geoFetcher.getLocation("Mannheim", description));
		locations1.add(geoFetcher.getLocation("Berlin", description));
		locations1.add(geoFetcher.getLocation("London", description));

		Set<Location> locations2 = new HashSet<Location>();
		locations2.add(geoFetcher.getLocation("Thal", description));
		locations2.add(geoFetcher.getLocation("Californien", description));
		locations2.add(geoFetcher.getLocation("Wien", description));
		locations2.add(geoFetcher.getLocation("MŸnchen", description));

		List<CompareResult> results = comparator
				.compare(locations1, locations2);

		for (CompareResult result : results) {
			printResult(result);
			System.out.println("\n");
		}
	}

	public void printResult(CompareResult result) {
		System.out.println(String.format("%s: %s", result.getDesc1(),
				result.getValue()));
		for (CompareResult rs : result.getSubresults()) {
			printResult(rs);
		}
	}

}
