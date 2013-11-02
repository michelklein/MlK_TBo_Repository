package de.uni.mannheim.semantic;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;

import de.uni.mannheim.semantic.model.Location;
import de.uni.mannheim.semantic.util.Util;

public class FetchGeoData {

	public static final void main(String[] argv) throws IOException,
			XPathExpressionException, ParserConfigurationException,
			SAXException {
		FetchGeoData sample = new FetchGeoData();
		Location location = sample.getLocation(7.2914, 50.7699);
		Util.print(location);

		location = sample.getLocation("Thal,_Styria");
		Util.print(location);
	}

	//
	// public static void test() {
	// final Geocoder geocoder = new Geocoder();
	// String lng = "7.2914";
	// String lat = "50.7699";
	// GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
	// .setLocation(new LatLng(lat, lng)).setLanguage("en")
	// .getGeocoderRequest();
	// GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
	// for (GeocoderResult result : geocoderResponse.getResults()) {
	// System.out.println(result);
	// }
	// }

	public Location getLocation(String name) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
				.setAddress(name).setLanguage("en").getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		return getLocationForGeoResponse(geocoderResponse, null, null);
	}

	public Location getLocation(String longitude, String latitude) {
		if (longitude == null || latitude == null) {
			return null;
		}
		return getLocation(Double.valueOf(longitude), Double.valueOf(latitude));
	}

	public Location getLocation(double longitude, double latitude) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
				.setLocation(
						new LatLng(String.valueOf(latitude), String
								.valueOf(longitude))).setLanguage("en")
				.getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		return getLocationForGeoResponse(geocoderResponse, longitude, latitude);
	}

	private Location getLocationForGeoResponse(
			GeocodeResponse geocoderResponse, Double longitude, Double latitude) {
		String placeName = null;
		String postalCode = null;
		String state = null;
		String country = null;
		Double lgn = longitude;
		Double lat = latitude;

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
		return new Location(lgn, lat, placeName, null, country,
				state, postalCode);
	}

	// public static void test2() throws IOException, XPathExpressionException,
	// ParserConfigurationException, SAXException {
	// // query address
	// // String address = "1600 Amphitheatre Parkway, Mountain View, CA";
	// // prepare a URL to the geocoder
	// // URL url = new URL(GEOCODER_REQUEST_PREFIX_FOR_XML + "?address=" +
	// // URLEncoder.encode(address, "UTF-8") + "&sensor=false");
	//
	// // californien
	// // String latlng = "38.3006,-76.5312";
	// // URL url = new URL(GEOCODER_REQUEST_PREFIX_FOR_XML + "?latlng="
	// // + URLEncoder.encode(latlng, "UTF-8") + "&sensor=false");
	//
	// // Hennef
	// String latlng = "50.7699,7.2914";
	// URL url = new URL(GEOCODER_REQUEST_PREFIX_FOR_XML + "?latlng="
	// + URLEncoder.encode(latlng, "UTF-8") + "&sensor=false");
	//
	// // prepare an HTTP connection to the geocoder
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	//
	// Document geocoderResultDocument = null;
	// try {
	// // open the connection and get results as InputSource.
	// conn.connect();
	// InputSource geocoderResultInputSource = new InputSource(
	// conn.getInputStream());
	//
	// // read result and parse into XML Document
	// geocoderResultDocument = DocumentBuilderFactory.newInstance()
	// .newDocumentBuilder().parse(geocoderResultInputSource);
	// } finally {
	// conn.disconnect();
	// }
	//
	// // prepare XPath
	// XPath xpath = XPathFactory.newInstance().newXPath();
	//
	// // extract the result
	// NodeList resultNodeList = null;
	//
	// // a) obtain the formatted_address field for every result
	// resultNodeList = (NodeList) xpath.evaluate(
	// "/GeocodeResponse/result/formatted_address",
	// geocoderResultDocument, XPathConstants.NODESET);
	// for (int i = 0; i < resultNodeList.getLength(); ++i) {
	// System.out.println("Land: "
	// + resultNodeList.item(i).getTextContent());
	// }
	//
	// // b) extract the locality for the first result
	// resultNodeList = (NodeList) xpath
	// .evaluate(
	// "/GeocodeResponse/result[1]/address_component[type/text()='locality']/long_name",
	// geocoderResultDocument, XPathConstants.NODESET);
	// for (int i = 0; i < resultNodeList.getLength(); ++i) {
	// System.out.println("Stadt: "
	// + resultNodeList.item(i).getTextContent());
	// }
	//
	// // c) extract the coordinates of the first result
	// resultNodeList = (NodeList) xpath.evaluate(
	// "/GeocodeResponse/result[1]/geometry/location/*",
	// geocoderResultDocument, XPathConstants.NODESET);
	// float lat = Float.NaN;
	// float lng = Float.NaN;
	// for (int i = 0; i < resultNodeList.getLength(); ++i) {
	// Node node = resultNodeList.item(i);
	// if ("lat".equals(node.getNodeName()))
	// lat = Float.parseFloat(node.getTextContent());
	// if ("lng".equals(node.getNodeName()))
	// lng = Float.parseFloat(node.getTextContent());
	// }
	// System.out.println("lat/lng=" + lat + "," + lng);
	//
	// // c) extract the coordinates of the first result
	// resultNodeList = (NodeList) xpath
	// .evaluate(
	// "/GeocodeResponse/result[1]/address_component[type/text() = 'administrative_area_level_1']/country[short_name/text() = 'US']/*",
	// geocoderResultDocument, XPathConstants.NODESET);
	// float lat2 = Float.NaN;
	// float lng2 = Float.NaN;
	// for (int i = 0; i < resultNodeList.getLength(); ++i) {
	// Node node = resultNodeList.item(i);
	// if ("lat".equals(node.getNodeName()))
	// lat2 = Float.parseFloat(node.getTextContent());
	// if ("lng".equals(node.getNodeName()))
	// lng2 = Float.parseFloat(node.getTextContent());
	// }
	// System.out.println("lat/lng=" + lat + "," + lng);
	// }

}
