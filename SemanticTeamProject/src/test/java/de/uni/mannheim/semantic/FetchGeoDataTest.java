package de.uni.mannheim.semantic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.uni.mannheim.semantic.model.Location;

public class FetchGeoDataTest {
	
	private FetchGeoData fetcher = new FetchGeoData();
	
	@Test
	public void testGetLocationString() {
		Location location = fetcher.getLocation(null);
		assertNull(location);
		
		location = fetcher.getLocation("");
		assertNull(location);
		
		location = fetcher.getLocation("213wddas32");
		assertNull(location);
		
		location = fetcher.getLocation("Hennef");
		assertEquals("Germany", location.getCountry());
		assertEquals("53773", location.getPostalCode());
		assertEquals("North Rhine-Westphalia", location.getState());
		assertEquals(new Double(7.283333000000001), location.getLongitude());
		assertEquals(new Double(50.773), location.getLatitude());
		
	}

	@Test
	public void testGetLocationStringString() {
		Location location = fetcher.getLocation(null, "");
		assertNull(location);
		
		location = fetcher.getLocation("", "");
		assertNull(location);
		
		location = fetcher.getLocation("", null);
		assertNull(location);
		
		location = fetcher.getLocation("7.283333000000001", null);
		assertNull(location);
		
		location = fetcher.getLocation(null, "50.773");
		assertNull(location);
		
		location = fetcher.getLocation("7.283333000000001", "");
		assertNull(location);
		
		location = fetcher.getLocation("", "50.773");
		assertNull(location);
		
		location = fetcher.getLocation("0", "0");
		assertNull(location);
		
		location = fetcher.getLocation("-123124121.123", "-123332223.122");
		assertNull(location);
		
		location = fetcher.getLocation("7.283333000000001", "50.773");
		assertEquals("Germany", location.getCountry());
		assertEquals("53773", location.getPostalCode());
		assertEquals("North Rhine-Westphalia", location.getState());
		assertEquals(new Double(7.283333000000001), location.getLongitude());
		assertEquals(new Double(50.773), location.getLatitude());
	}

	@Test
	public void testGetLocationDoubleDouble() {
		Location location = fetcher.getLocation(new Double(23), null);
		assertNull(location);
		
		location = fetcher.getLocation(null, new Double(42));
		assertNull(location);
		
		
		location = fetcher.getLocation(7.283333000000001, null);
		assertNull(location);
		
		location = fetcher.getLocation(null, 50.773);
		assertNull(location);
		
		location = fetcher.getLocation(0.0, 0.0);
		assertNull(location);
		
		location = fetcher.getLocation(-123124121.123, -123332223.122);
		assertNull(location);
		
		location = fetcher.getLocation(7.283333000000001, 50.773);
		assertEquals("Germany", location.getCountry());
		assertEquals("53773", location.getPostalCode());
		assertEquals("North Rhine-Westphalia", location.getState());
		assertEquals(new Double(7.283333000000001), location.getLongitude());
		assertEquals(new Double(50.773), location.getLatitude());
	}

}
