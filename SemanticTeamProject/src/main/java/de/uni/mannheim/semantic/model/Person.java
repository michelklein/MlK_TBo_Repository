package de.uni.mannheim.semantic.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	private Date birthdate;
	private List<Interest> interests;
	private Set<Location> locations;

	public Person(String firstname, String lastname, Date birthdate, Set<Location> locations,
			List<Interest> interest, String imageURL) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.interests = interest;
		this.birthdate = birthdate;
		this.imageURL = imageURL;
		this.locations = locations;
	}
	
	public Person(String firstname, String lastname, String birthdate, Set<Location> locations,
			List<Interest> interest, String imageURL) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.interests = interest;
		this.imageURL = imageURL;
		this.locations = locations;
		try {
			this.birthdate = sdfToDate.parse(birthdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}


	public Date getBirthdate() {
		return birthdate;
	}

	public Set<Location> getLocations() {
		return locations;
	}

	/* GUI Functions */

	public String getFormattedBirthday() {
		return sdfToDate2.format(birthdate).toString();
	}
	
//	public String getFormattedLocs(List<Institution> i) {
//		String result = "";
//		for (Institution institution : i) {
//			result = result + institution.getName();
//		}
//		return result;
//	}

	public String toString() {
		if (birthdate != null) {
			return firstname + " " + lastname + " "
					+ sdfToDate2.format(birthdate) + "\n";
		} else {
			return firstname + " " + lastname + "\n";
		}
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
