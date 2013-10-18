package de.uni.mannheim.semantic.model;

import java.util.Date;
import java.util.List;

public class FBPerson extends Person {

	public FBPerson(String firstname, String name, Institution home,
			Institution birthplace, Date birthdate, String currLocation,
			List<Institution> education, List<Institution> employer) {
		super(firstname, name, home, birthplace, birthdate, currLocation, education,
				employer);
		// TODO Auto-generated constructor stub
	}

}
