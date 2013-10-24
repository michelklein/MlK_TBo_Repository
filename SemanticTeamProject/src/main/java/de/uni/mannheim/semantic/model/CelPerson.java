package de.uni.mannheim.semantic.model;

import java.util.Date;
import java.util.List;

public class CelPerson extends Person {

	public CelPerson(String firstname, String name, Institution home,
			Institution location, Date birthdate, Institution currLocation,
			List<Institution> education, List<Institution> employer, List<String> interest, String picURL) {
		super(firstname, name, home, location, birthdate, currLocation, education,
				employer, interest,picURL);
		// TODO Auto-generated constructor stub
	}
	
	
	
}
