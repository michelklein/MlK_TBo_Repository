package de.uni.mannheim.semantic.comparison;

import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Location;

public class LocationComparator extends AbstractComparator<Location> {

	private static final int EARTH_RADIUS = 6371;
	private static final int DISTANCE_PERCENT = 40;
	private static final int COUNTRY_PERCENT = 10;
	private static final int STATE_PERCENT = 15;
	private static final int POSTAL_CODE_PERCENT = 25;
	private static final int TIMEZONE_PERCENT = 10;
	private static final int MAX_DISTANCE = 20000;
	private static final int MAX_TIMEZONE_DIFFERNCE = 24;

	private PostalCodeComparator postalCodeComparator = new PostalCodeComparator();

	@Override
	public CompareResult compare(Location o1, Location o2) {
		this.o1 = o1;
		this.o2 = o2;
		result = new CompareResult(0, "Overall", o1, o2);

		// Distance
		float distance = calculateDistance(o1, o2);
		float percent = 0;
		if (distance < MAX_DISTANCE) {
			// calculate difference between max distance and current distance
			float difference = (MAX_DISTANCE - distance);
			float distancePercent = ((float) (100f / MAX_DISTANCE) * difference) / 100;
			percent = DISTANCE_PERCENT * distancePercent;
		}
		result.getSubresults()
				.add(new CompareResult((int) percent, "Distance",o1,o2));
		result.setValue((int) (result.getValue() + percent));

		// country
		compareHelper(o1.getCountry(), o2.getCountry(), COUNTRY_PERCENT,
				"Country", result);
		// state
		compareHelper(o1.getState(), o2.getState(), STATE_PERCENT, "State",
				result);

		// postal code
		CompareResult postalCodeResult = postalCodeComparator.compare(
				o1.getPostalCode(), o2.getPostalCode());
		result.getSubresults().add(postalCodeResult);
		result.setValue((int) (result.getValue() + postalCodeResult.getValue()));

		// time zone
		int difference = o1.getOffsetUTC() - o2.getOffsetUTC();
		percent = (((float) MAX_TIMEZONE_DIFFERNCE - (float) difference) / (float) MAX_TIMEZONE_DIFFERNCE);
		float timeZoneValue = percent * TIMEZONE_PERCENT;
		result.getSubresults()
				.add(new CompareResult((int) timeZoneValue, "Timezone",o1,o2));
		result.setValue((int) (result.getValue() + timeZoneValue));
		return result;
	}

	private float calculateDistance(Location location1, Location location2) {
		return calculateDistance(location1.getLatitude(),
				location1.getLongitude(), location2.getLatitude(),
				location2.getLongitude());
	}

	private float calculateDistance(double lat1, double lon1, double lat2,
			double lon2) {
		float dLat = (float) Math.toRadians(lat2 - lat1);
		float dLon = (float) Math.toRadians(lon2 - lon1);
		float a = (float) (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math
				.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2))
				* Math.sin(dLon / 2) * Math.sin(dLon / 2));
		float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
		float d = EARTH_RADIUS * c;
		return d;
	}

	private class PostalCodeComparator extends AbstractComparator<String> {

		@Override
		public CompareResult compare(String o1, String o2) {
			result = new CompareResult(0, "Postal Code", o1, o2);
			if(o1 != null && o2 != null) {
				result = new CompareResult(0, "Postal Code", o1, o2);
				int count = sameLetters(o1, o2);
				float ko = (float) count / (float) o1.length();
				result.setValue((int) (POSTAL_CODE_PERCENT * ko));
			} 
			return result;
		}

		private int sameLetters(String o1, String o2) {
			int count = 0;
			if (o1.length() != o2.length()) {
				return count;
			}
			for (int i = 0; i < o1.length(); i++) {
				if (o1.charAt(i) == o2.charAt(i)) {
					count++;
				} else {
					return count;
				}
			}
			return count;
		}

	}

}
