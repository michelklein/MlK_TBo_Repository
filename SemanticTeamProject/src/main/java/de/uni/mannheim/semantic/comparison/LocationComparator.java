package de.uni.mannheim.semantic.comparison;

import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Institution;
import de.uni.mannheim.semantic.model.Location;

public class LocationComparator extends AbstractComparator<Location> {

	private static final int EARTH_RADIUS = 6371;
	private static final int DISTANCE_PERCENT = 50;
	private static final int COUNTRY_PERCENT = 10;
	private static final int STATE_PERCENT = 15;
	private static final int POSTAL_CODE_PERCENT = 25;
	private static final int MAX_DISTANCE = EARTH_RADIUS / 2;

	@Override
	public CompareResult compare(Location o1, Location o2) {
		this.o1 = o1;
		this.o2 = o2;
		result = new CompareResult();
		result.setDescription("Overall");
		
		// Distance
		float distance = calculateDistance(o1, o2);
		float percent = 0;
		if (distance < MAX_DISTANCE) {
			// calculate difference between max distance and current distance
			float difference = (MAX_DISTANCE - distance);
			float distancePercent = ((float) (100f / MAX_DISTANCE) * difference) / 100;
			percent = DISTANCE_PERCENT * distancePercent;
		}
		result.getSubrestults().add(
				new CompareResult((int) percent, "Distance"));
		result.setValue((int) (result.getValue() + percent));

		// country
		compareHelper(o1.getCountry(), o2.getCountry(), COUNTRY_PERCENT,
				"Country", result);
		// state
		compareHelper(o1.getState(), o2.getState(), STATE_PERCENT,
				"State", result);
		// postal code
		compareHelper(o1.getPostalCode(), o2.getPostalCode(), POSTAL_CODE_PERCENT,
				"Postal Code", result);

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

}
