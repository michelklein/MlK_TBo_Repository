package de.uni.mannheim.semantic.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.uni.mannheim.semantic.comparison.DateComparator;

/**
 * This class represents objects which could be compared with the {@link DateComparator}
 * 
 * @author Michel
 */
public class DateObject extends AbstractToolTipInfo {

	private static SimpleDateFormat sdfToDate2 = new SimpleDateFormat(
			"EEEE, dd.MM.yyyy");
	
	public static final String BIRTHDATE = "Birthdate";
	public static final String RELEASE_DATE = "Release Date";
	public static final String START_DATE = "Start Date";
	public static final String END_DATE = "End Date";

	private boolean prio;
	private String description;
	private Date date;
	private String toolTipInfo;
	
	public DateObject(Date date, String description,String toolTipInfo) {
		super();
		this.prio = getPrio(description);
		this.description = description;
		this.date = date;
		this.toolTipInfo = toolTipInfo;
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

	@Override
	public String getToolTipInfo() {
		return toolTipInfo;
	}
	
}
