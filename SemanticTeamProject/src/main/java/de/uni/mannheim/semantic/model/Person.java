package de.uni.mannheim.semantic.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This represents both a facebook and a celebrity Person. 
 * @author Michel
 */
public class Person extends AbstractToJson {

	private String firstname;
	private String lastname;
	private String imageURL;
	private List<Interest> interests;
	private Set<Location> locations;
	private List<DateObject> dates;

	public Person(String firstname, String lastname, List<DateObject> dates, Set<Location> locations,
			List<Interest> interest, String imageURL) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.interests = interest;
		this.imageURL = imageURL;
		this.locations = locations;
		this.dates = dates;
	}
	
	public List<DateObject> getDates() {
		return dates;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public Set<Location> getLocations() {
		return locations;
	}

	public String toString() {
		return String.format("%s %s",  firstname, lastname);
	}

	public List<Interest> getInterest() {
		return interests;
	}

	public String getImageURL() {
		return imageURL;
	}
	public Set<String> getAllGenres() {
		Set<String> genreList = new HashSet<String>();
		for (Interest i : getInterest()) {
			if(!i.getGenre().equals(""))
			genreList.addAll(i.getGenre());
		}
		return genreList;
	}

}
