package de.uni.mannheim.semantic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderGeometry;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

import de.uni.mannheim.semantic.model.Location;
import facebook4j.internal.org.json.JSONObject;

public class FetchGeoData {

	private static final String TIMEZONE_URL = "https://maps.googleapis.com/maps/api/timezone/json?";

	private Logger logger = LogManager.getLogger(FetchGeoData.class
			.getName());
	
	public Location getLocation(String name, String description) {
		logger.info(String.format("load location for name: %s and descrption: %s", name, description));
		if (name == null) {
			logger.warn("name should not be null");
			return null;
		}
		
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
				.setAddress(name).setLanguage("en").getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		if (geocoderResponse != null && geocoderResponse.getResults() != null
				&& geocoderResponse.getResults().size() > 0) {
			GeocoderGeometry geometry = geocoderResponse.getResults().get(0)
					.getGeometry();
			Location location = getLocation(geometry.getLocation().getLng().doubleValue(),
					geometry.getLocation().getLat().doubleValue(), description);
			return location;
		} else {
			logger.info("no location found!");
			return null;
		}
	}

	public Location getLocation(String longitude, String latitude,
			String description) {
		logger.info(String.format("load location for longitude: %s, latitude: %s and descrption: %s",longitude, latitude, description));
		if (longitude == null || latitude == null || longitude == ""
				|| latitude == "") {
			logger.warn("longitude/latitude should not be null");
			return null;
		}
		return getLocation(Double.valueOf(longitude), Double.valueOf(latitude),
				description);
	}

	public Location getLocation(Double longitude, Double latitude,
			String description) {
		logger.info(String.format("load location for longitude: %s, latitude: %s and descrption: %s",longitude, latitude, description));
		if (longitude == null || latitude == null) {
			logger.warn("longitude/latitude should not be null");
			return null;
		}
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
				.setLocation(
						new LatLng(String.valueOf(latitude), String
								.valueOf(longitude))).setLanguage("en")
				.getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		Location location = getLocationForGeoResponse(geocoderResponse, longitude, latitude,
				description);
		logger.info(String.format("location found: %s", location.getFormattedLocation()));
		return location;
	}

	private Location getLocationForGeoResponse(
			GeocodeResponse geocoderResponse, Double longitude,
			Double latitude, String description) {
		String placeName = null;
		String postalCode = null;
		String state = null;
		String country = null;
		Double lgn = longitude;
		Double lat = latitude;

		if (geocoderResponse.getStatus().equals(GeocoderStatus.ZERO_RESULTS)) {
			return null;
		} 

		for (GeocoderResult result : geocoderResponse.getResults()) {
			for (GeocoderAddressComponent address : result
					.getAddressComponents()) {
				if (address.getTypes().contains("locality")) {
					placeName = address.getLongName();
				} else if (address.getTypes().contains(
						"administrative_area_level_1")) {
					state = address.getLongName();
				} else if (address.getTypes().contains("country")) {
					country = address.getLongName();
				} else if (address.getTypes().contains("postal_code")) {
					postalCode = address.getLongName();
				}
				if (lgn == null) {
					lgn = result.getGeometry().getLocation().getLng()
							.doubleValue();
				}
				if (lat == null) {
					lat = result.getGeometry().getLocation().getLat()
							.doubleValue();
				}
			}
			if (placeName != null && postalCode != null && state != null
					&& country != null && lat != null && lgn != null) {
				break;
			}
		}

		return new Location(lgn, lat, placeName,  country, state,
				postalCode, getOffsetUTC(longitude, latitude), description);
	}

	public Integer getOffsetUTC(Double longitude, Double latitude) {
		HttpURLConnection conn = null;
		try {
			// prepare a URL to the geocoder
			URL url = new URL(TIMEZONE_URL + "location=" + latitude.toString()
					+ "," + longitude.toString() + "&timestamp="
					+ System.currentTimeMillis() / 1000 + "&sensor=false");

			// prepare an HTTP connection to the geocoder
			conn = (HttpURLConnection) url.openConnection();

			// open the connection and get results as InputSource.
			conn.connect();
			InputStream inputStream = conn.getInputStream();
			InputStreamReader is = new InputStreamReader(inputStream);
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(is);
			String read = br.readLine();

			while (read != null) {
				sb.append(read);
				read = br.readLine();

			}
			JSONObject json = new JSONObject(sb.toString());
			Integer offsetUTC = new Integer(json.getString("rawOffset"));
			return offsetUTC / 3600;
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			if (conn != null)
				conn.disconnect();
		}

		return null;
	}

}
