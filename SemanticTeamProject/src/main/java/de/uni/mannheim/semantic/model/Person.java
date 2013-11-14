package de.uni.mannheim.semantic.model;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Person extends AbstractToJson {
	private static SimpleDateFormat sdfToDate = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static SimpleDateFormat sdfToDate2 = new SimpleDateFormat(
			"EEEE, dd.MM.yyyy");

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
	
	
	
//	public Person(String firstname, String lastname, String birthdate, Set<Location> locations,
//			List<Interest> interest, String imageURL) {
//		super();
//		this.firstname = firstname;
//		this.lastname = lastname;
//		this.interests = interest;
//		this.imageURL = imageURL;
//		this.locations = locations;
//		try {
//			this.birthdate = sdfToDate.parse(birthdate);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}

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
