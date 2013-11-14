package de.uni.mannheim.semantic.comparison;

import static org.junit.Assert.*;

import org.junit.Test;

import de.uni.mannheim.semantic.FetchDataServlet;
import de.uni.mannheim.semantic.FetchGeoData;
import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Location;

public class LocationComparatorTest {

	private String description = "Test-Location";
	private FetchGeoData geoDataFetcher = new FetchGeoData();
	private LocationComparator locationComparator = new LocationComparator();

	@Test
	public void test() {
		Location hennef = geoDataFetcher.getLocation(null,7.2914, 50.7699,
				description);
		Location siegburg = geoDataFetcher.getLocation(null,7.2234, 50.8027,
				description);
		Location kšln = geoDataFetcher
				.getLocation(null,7.0416, 50.8998, description);
		Location berlin = geoDataFetcher.getLocation(null,13.2905, 52.4827,
				description);
		Location mannheim = geoDataFetcher.getLocation(null,8.5316, 49.5121,
				description);
		Location californien = geoDataFetcher.getLocation(null,-76.5312, 38.3006,
				description);

		// Location hennef = new Location(7.2914, 50.7699, "Hennef", "Germany",
		// "NRW", "53773", 1, description);
		// Location californien = new Location(-76.5312, 38.3006, "California",
		// "USA", "California", "77481", -5, description);

		// compare hennef with hennef
		CompareResult compareResult = locationComparator
				.compare(hennef, hennef);
		locationComparator.print();
		assertEquals(100, compareResult.getValue());

		// compare hennef with californien
		compareResult = locationComparator.compare(hennef, californien);
		locationComparator.print();
		assertEquals(34, compareResult.getValue());

		// compare hennef with siegburg
		compareResult = locationComparator.compare(hennef, siegburg);
		locationComparator.print();

		// compare hennef with kšln
		compareResult = locationComparator.compare(hennef, kšln);
		locationComparator.print();

		// compare hennef with berlin
		compareResult = locationComparator.compare(hennef, berlin);
		locationComparator.print();

		// compare hennef with mannheim
		compareResult = locationComparator.compare(hennef, mannheim);
		locationComparator.print();

	}

	@Test
	public void test2() {
		Location hennef = geoDataFetcher.getLocation(null,7.2914, 50.7699,
				description);
		Location india = geoDataFetcher.getLocation("400014 Mumbai, India",
				description);
		locationComparator.compare(hennef, india);
		locationComparator.print();

	}
}
