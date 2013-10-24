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
	private String picURL;
	private String name;
	private Date birthdate;

	private List<String> interest;
	
	private Institution currLocation;

	private Institution home;
	private Institution location;
	private List<Institution> education;
	private List<Institution> employer;

	public Person(String firstname, String name, Institution home,
			Institution location, Date birthdate, Institution currLocation,
			List<Institution> education, List<Institution> employer, List<String> interest, String picURL) {
		super();
		this.firstname = firstname;
		this.name = name;
		this.home = home;
		this.interest = interest;
		this.birthdate = birthdate;
		this.location = location;
		this.currLocation = currLocation;
		this.education = education;
		this.employer = employer;
		this.picURL=picURL;
	}

	public Person(String firstname, String name, Institution home,

			Institution birthplace, String birthdate, Institution currLocation,


			List<Institution> education, List<Institution> employer) {
		super();
		this.firstname = firstname;
		this.name = name;
		this.home = home;
		this.location = birthplace;
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

	public Institution getLocation() {
		return location;
	}

	public Date getBirthdate() {
		return birthdate;
	}


	public Institution getCurrLocation() {
	return currLocation;
	}

	public List<Institution> getEducation() {
		return education;
	}

	public List<Institution> getEmployer() {
		return employer;
	}

	
	
	
	
	
	/*GUI Functions*/
	

	public String getFormattedBirthday() {
		return getBirthdate().toLocaleString();
	}
	public String getFormattedLocs(List<Institution> i){
		String result="";
		for (Institution institution : i) {
			result=result+institution.getName();
		}
		return result;
	}
	public String toString() {
		if (birthdate != null) {
			return firstname + " " + name + " " + sdfToDate2.format(birthdate)
					+ "\n";
		} else {
			return firstname + " " + name + "\n";
		}
	}

	public List<String> getInterest() {
		return interest;
	}

	
	public String getPicURL() {
		return picURL;
	}

	



}
