package de.uni.mannheim.semantic.model;

import java.util.Date;
import java.util.List;

public class FacebookPerson extends Person {

	public FacebookPerson(String firstname, String name, Institution home,
			Institution location, Date birthdate, Institution currLocation,
			List<Institution> education, List<Institution> employer, List<Interest> interests, String picURL) {
		super(firstname, name, home, location, birthdate, currLocation, education,
				employer, interests,picURL);
		// TODO Auto-generated constructor stub
	}
	
	
	
}
