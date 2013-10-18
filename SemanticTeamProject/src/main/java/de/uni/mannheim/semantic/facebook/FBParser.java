package de.uni.mannheim.semantic.facebook;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.uni.mannheim.semantic.model.FBPerson;
import de.uni.mannheim.semantic.model.Institution;
import facebook4j.Achievement;
import facebook4j.Activity;
import facebook4j.Checkin;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.IdNameEntity;
import facebook4j.Location;
import facebook4j.Page;
import facebook4j.User.Education;

public class FBParser {
	private Facebook fb;
	private FBPerson p;
	public FBParser(Facebook f) {
		// TODO Auto-generated constructor stub
		fb = f;
		
		try {
			fb.getCheckins().get(0).getPlace().getLocation().getLatitude();
			String firstname = fb.getMe().getFirstName();
			String name = fb.getMe().getLastName();
			Institution home = new Institution();
			Institution birthplace = new Institution();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date birthdate = df.parse(fb.getMe().getBirthday());
			Institution currLocation = fetchDataForID(fb.getMe().getLocation().getId());
			List<Institution> education = fetchMoreDataForID(fb.getMe()
					.getEducation());
			;
			List<Institution> employer = new ArrayList<Institution>();
			p = new FBPerson(firstname, name, home, birthplace,
					birthdate, currLocation, education, employer);

			TBoSuperDuperPrinter(p);

		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Institution fetchDataForID(String id) throws FacebookException {
		System.out.println(id);
		// TODO Auto-generated method stub
		Institution i = new Institution();
		for (Page p : fb.searchPages(id)) {
			
			System.out.println(p.getId());
			i.setName(p.getName());
			
			i.setLoc(new de.uni.mannheim.semantic.model.Location(p.getLocation().getLatitude(),p.getLocation().getLongitude()));
						}
		
		return i;
	}

	private List<Institution> fetchMoreDataForID(List<Education> list)
			throws FacebookException {

		
		for (Education e : list) {

			for (Location l : fb.searchLocations(e.getSchool().getId())) {
l.getPlace().getLocation().getLatitude();
			}
		}
		return new ArrayList<Institution>();

	}

	private void TBoSuperDuperPrinter(FBPerson p) {
		try {
			// TODO Auto-generated method stub
			for (Field field : getInheritedFields(FBPerson.class)) {
				field.setAccessible(true);
				String fname = field.getName();
				Object value;

				value = field.get(p);

//				System.out.println("Field: " + fname + " : " + value);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<Field> getInheritedFields(Class<?> type) {
		List<Field> fields = new ArrayList<Field>();
		for (Class<?> c = type; c != null; c = c.getSuperclass()) {
			fields.addAll(Arrays.asList(c.getDeclaredFields()));
		}
		return fields;
	}

	public FBPerson getP() {
		return p;
	}

	
	
	
	

}
