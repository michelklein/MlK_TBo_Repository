package de.uni.mannheim.semantic;

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

public class FetchGeoData {

	public Location getLocation(String name, String description) {
		if (name == null) {
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
			return getLocation(geometry.getLocation().getLng().doubleValue(),
					geometry.getLocation().getLat().doubleValue(), description);
		} else {
			return null;
		}
	}

	public Location getLocation(String longitude, String latitude,
			String description) {
		if (longitude == null || latitude == null || longitude == ""
				|| latitude == "") {
			return null;
		}
		return getLocation(Double.valueOf(longitude), Double.valueOf(latitude),
				description);
	}

	public Location getLocation(Double longitude, Double latitude,
			String description) {
		if (longitude == null || latitude == null) {
			return null;
		}
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
				.setLocation(
						new LatLng(String.valueOf(latitude), String
								.valueOf(longitude))).setLanguage("en")
				.getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		return getLocationForGeoResponse(geocoderResponse, longitude, latitude,
				description);
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
		return new Location(lgn, lat, placeName, null, country, state,
				postalCode, description);
	}

}
