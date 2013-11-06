package de.uni.mannheim.semantic.model;

public class Location {

	public static final String BIRTHPLACE = "Birthplace";
	public static final String DEATHPLACE = "Deathplace";
	public static final String WORKPLACE = "Workplace";
	public static final String EDUCATIONPLACE = "Educationplace";
	public static final String CURRENT_LOCATION = "Current Location";
	
	private Double longitude;
	private Double latitude;
	private Integer offsetUTC;
	private String name;
	private String country;
	private String state;
	private String postalCode;
	private String formattedLocation;

	public Location(Double longitude, Double latitude, String name,
			String country, String state, String postalCode, Integer offsetUTC,
			String description) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.name = name;
		this.country = country;
		this.state = state;
		this.postalCode = postalCode;
		this.offsetUTC = offsetUTC;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((postalCode == null) ? 0 : postalCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		return true;
	}

	public Integer getOffsetUTC() {
		return offsetUTC;
	}
	

}
