package de.uni.mannheim.semantic.model;

public class Location {

	public static final String BIRTHPLACE = "Birthplace";
	public static final String DEATHPLACE = "Deathplace";
	public static final String WORKPLACE = "Workplace";
	public static final String EDUCATIONPLACE = "Educationplace";
	public static final String CURRENT_LOCATION = "Current Location";
	
	private Double longitude;
	private Double latitude;
	private String name;
	private String country;
	private String state;
	private String continent;
	private String postalCode;
	private String formattedLocation;

	public Location(Double longitude, Double latitude, String name,
			String continent, String country, String state, String postalCode,
			String description) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.name = name;
		this.country = country;
		this.state = state;
		this.continent = continent;
		this.postalCode = postalCode;
		if (description != null) {
			formattedLocation = String.format("%s: %s %s, %s", description, postalCode, name,
					country);
		} else {
			formattedLocation = String.format("%s %s, %s", postalCode, name,
					country);
		}
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String toString() {
		return formattedLocation;
	}

	public String getFormattedLocation() {
		return formattedLocation;
	}

}
