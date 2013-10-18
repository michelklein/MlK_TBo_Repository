package de.uni.mannheim.semantic.model;

import java.util.Date;
import java.util.List;

public class Person {

	
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
	
	
	
	
}
