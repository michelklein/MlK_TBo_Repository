package de.uni.mannheim.semantic.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Person {
	private static SimpleDateFormat sdfToDate = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static SimpleDateFormat sdfToDate2 = new SimpleDateFormat(
			"dd.MM.yyyy");

	private String firstname;
	private String name;
	private Date birthdate;
	private String currLocation;

	private Institution home;
	private Institution birthplace;
	private List<Institution> education;
	private List<Institution> employer;

	public Person(String firstname, String name, Institution home,
			Institution birthplace, Date birthdate, String currLocation,
			List<Institution> education, List<Institution> employer) {
		super();
		this.firstname = firstname;
		this.name = name;
		this.home = home;
		this.birthplace = birthplace;
		this.birthdate = birthdate;
		this.currLocation = currLocation;
		this.education = education;
		this.employer = employer;
	}

	public Person(String firstname, String name, Institution home,
			Institution birthplace, String birthdate, String currLocation,
			List<Institution> education, List<Institution> employer) {
		super();
		this.firstname = firstname;
		this.name = name;
		this.home = home;
		this.birthplace = birthplace;
		try {
			this.birthdate = sdfToDate.parse(birthdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.currLocation = currLocation;
		this.education = education;
		this.employer = employer;

	}

	public String getFirstname() {
		return firstname;
	}

	public String getName() {
		return name;
	}

	public Institution getHome() {
		return home;
	}

	public Institution getBirthplace() {
		return birthplace;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public String getCurrLocation() {
		return currLocation;
	}

	public List<Institution> getEducation() {
		return education;
	}

	public List<Institution> getEmployer() {
		return employer;
	}

	public String getFormattedBirthday() {
		return getBirthdate().toLocaleString();
	}

	public String toString() {
		if (birthdate != null) {
			return firstname + " " + name + " " + sdfToDate2.format(birthdate)
					+ "\n";
		} else {
			return firstname + " " + name + "\n";
		}
	}

}
