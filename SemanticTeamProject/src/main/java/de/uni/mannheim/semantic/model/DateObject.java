package de.uni.mannheim.semantic.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateObject {

	private static SimpleDateFormat sdfToDate2 = new SimpleDateFormat(
			"EEEE, dd.MM.yyyy");
	
	public static final String BIRTHDATE = "Birthdate";
	public static final String RELEASE_DATE = "Release Date";
	public static final String START_DATE = "Start Date";
	public static final String END_DATE = "End Date";

	private boolean prio;
	private String description;
	private Date date;
	private String name;
	
	public DateObject(Date date, String description,String name) {
		super();
		this.prio = getPrio(description);
		this.description = description;
		this.date = date;
		this.name = name;
		System.out.println(description+" : "+date);
	}
	
	public boolean isPrio() {
		return prio;
	}
	public void setPrio(boolean prio) {
		this.prio = prio;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String toString() {
		return sdfToDate2.format(date).toString();
	}
	
	private boolean getPrio(String description) {
		if(RELEASE_DATE.equals(description)) {
			return false;
		}
		return true;
	}

	public String getName() {
		return name;
	}

	
}
