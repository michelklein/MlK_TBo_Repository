package de.uni.mannheim.semantic.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import facebook4j.internal.org.json.JSONObject;

public class Person {
	private static SimpleDateFormat sdfToDate = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static SimpleDateFormat sdfToDate2 = new SimpleDateFormat(
			"dd.MM.yyyy");

	private String firstname;
	private String lastname;
	private String imageURL;
	private Date birthdate;
	private List<String> interest;
	private Institution currentLocation;
	private Institution home;
	private Institution location;
	private List<Institution> education;
	private List<Institution> employer;

	public Person(String firstname, String lastname, Institution home,
			Institution location, Date birthdate, Institution currentLocation,
			List<Institution> education, List<Institution> employer,
			List<String> interest, String imageURL) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.home = home;
		this.interest = interest;
		this.birthdate = birthdate;
		this.location = location;
		this.currentLocation = currentLocation;
		this.education = education;
		this.employer = employer;
		this.imageURL = imageURL;
	}

	public Person(String firstname, String lastname, Institution home,
			Institution birthplace, String birthdate, Institution currentLocation,
			List<Institution> education, List<Institution> employer) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.home = home;
		this.location = birthplace;
		try {
			this.birthdate = sdfToDate.parse(birthdate);
		} catch (ParseException e) {
			// TODO catch and handle exception
			e.printStackTrace();
		}
		this.currentLocation = currentLocation;
		this.education = education;
		this.employer = employer;

	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public Institution getHome() {
		return home;
	}

	public Institution getLocation() {
		return location;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public Institution getCurrLocation() {
		return currentLocation;
	}

	public List<Institution> getEducation() {
		return education;
	}

	public List<Institution> getEmployer() {
		return employer;
	}

	/* GUI Functions */

	public String getFormattedBirthday() {
		return getBirthdate().toLocaleString();
	}

	public String getFormattedLocs(List<Institution> i) {
		String result = "";
		for (Institution institution : i) {
			result = result + institution.getName();
		}
		return result;
	}

	public String toString() {
		if (birthdate != null) {
			return firstname + " " + lastname + " "
					+ sdfToDate2.format(birthdate) + "\n";
		} else {
			return firstname + " " + lastname + "\n";
		}
	}

	public List<String> getInterest() {
		return interest;
	}

	public String getImageURL() {
		return imageURL;
	}
	
	public String toJsonString() {
		JSONObject json = new JSONObject(this);
		return json.toString();
	}

}
