package de.uni.mannheim.semantic.model;


public class Institution {
	private String name;
	private Location location;

	public Institution(String name, String longitude, String latitude) {
		this.name = name;
		if (longitude != null && latitude != null) {
			location = new Location(Double.parseDouble(longitude), Double.parseDouble(latitude));
		}
	}

	public Institution(String name, Double longitude, Double latitude) {
		this.name = name;
		location = new Location(longitude, latitude);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLoc(Location location) {
		this.location = location;
	}

}
