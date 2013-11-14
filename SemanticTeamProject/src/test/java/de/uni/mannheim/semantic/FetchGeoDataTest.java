package de.uni.mannheim.semantic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.uni.mannheim.semantic.model.Location;

public class FetchGeoDataTest {
	
	private FetchGeoData fetcher = new FetchGeoData();
	private String description = "Test-Location";
	@Test
	public void testGetLocationString() {
		Location location = fetcher.getLocation(null,description);
		assertNull(location);
		
		location = fetcher.getLocation("",description);
		assertNull(location);
		
		location = fetcher.getLocation("213wddas32",description);
		assertNull(location);
		
		location = fetcher.getLocation("Hennef",description);
		assertEquals("Germany", location.getCountry());
		assertEquals("53773", location.getPostalCode());
		assertEquals("North Rhine-Westphalia", location.getState());
		assertEquals(new Double(7.283333000000001), location.getLongitude());
		assertEquals(new Double(50.773), location.getLatitude());
		
	}

	@Test
	public void testGetLocationStringString() {
		Location location = fetcher.getLocation(null,null, "",description);
		assertNull(location);
		
		location = fetcher.getLocation(null,"", "",description);
		assertNull(location);
		
		location = fetcher.getLocation(null,"", null,description);
		assertNull(location);
		
		location = fetcher.getLocation(null,"7.283333000000001", null,description);
		assertNull(location);
		
		location = fetcher.getLocation(null,null, "50.773",description);
		assertNull(location);
		
		location = fetcher.getLocation(null,"7.283333000000001", "",description);
		assertNull(location);
		
		location = fetcher.getLocation(null,"", "50.773",description);
		assertNull(location);
		
		location = fetcher.getLocation(null,"0", "0",description);
		assertNull(location);
		
		location = fetcher.getLocation(null,"-123124121.123", "-123332223.122",description);
		assertNull(location);
		
		location = fetcher.getLocation(null,"7.283333000000001", "50.773",description);
		assertEquals("Germany", location.getCountry());
		assertEquals("53773", location.getPostalCode());
		assertEquals("North Rhine-Westphalia", location.getState());
		assertEquals(new Double(7.283333000000001), location.getLongitude());
		assertEquals(new Double(50.773), location.getLatitude());
	}

	@Test
	public void testGetLocationDoubleDouble() {
		Location location = fetcher.getLocation(null,new Double(23), null,description);
		assertNull(location);
		
		location = fetcher.getLocation(null,null, new Double(42),description);
		assertNull(location);
		
		
		location = fetcher.getLocation(null,7.283333000000001, null,description);
		assertNull(location);
		
		location = fetcher.getLocation(null,null, 50.773,description);
		assertNull(location);
		
		location = fetcher.getLocation(null,0.0, 0.0,description);
		assertNull(location);
		
		location = fetcher.getLocation(null,-123124121.123, -123332223.122,description);
		assertNull(location);
		
		location = fetcher.getLocation(null,.283333000000001, 50.773,description);
		assertEquals("Germany", location.getCountry());
		assertEquals("53773", location.getPostalCode());
		assertEquals("North Rhine-Westphalia", location.getState());
		assertEquals(new Double(7.283333000000001), location.getLongitude());
		assertEquals(new Double(50.773), location.getLatitude());
	}

}
