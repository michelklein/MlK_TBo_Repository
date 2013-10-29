package de.uni.mannheim.semantic.facebook;

import java.lang.reflect.Field;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sun.security.action.GetBooleanAction;
import de.uni.mannheim.semantic.model.FBPerson;
import de.uni.mannheim.semantic.model.Institution;
import facebook4j.Achievement;
import facebook4j.Activity;
import facebook4j.Checkin;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.IdNameEntity;
import facebook4j.Location;
import facebook4j.Movie;
import facebook4j.Page;
import facebook4j.Picture;
import facebook4j.PictureSize;
import facebook4j.ResponseList;
import facebook4j.User.Education;
import facebook4j.User.Work;

public class FBParser {
	private static Facebook fb;
	private FBPerson p;

	public static Facebook getFB() {
		return fb;
	}

	public FBParser(Facebook f) {
		// TODO Auto-generated constructor stub
		fb = f;
		Institution home = null;
		Institution location = null;
		Institution currLocation = null;

		try {

			// Picture
			String picURL = fb.getPictureURL(PictureSize.large).toString();
			// Firstname
			String firstname = fb.getMe().getFirstName();
			// Name
			String name = fb.getMe().getLastName();
			// Home
			IdNameEntity hometown = fb.getMe().getHometown();
			if (hometown != null) {
				home = new Institution(hometown.getId());
			}

			IdNameEntity locationTmp = fb.getMe().getLocation();
			if (locationTmp != null) {
				location = new Institution(locationTmp.getId());
			}

			// Interest
			List<String> inter = fb.getMe().getInterestedIn();
			// Birthdate
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date birthdate = df.parse(fb.getMe().getBirthday());

			// CurrLocation
			ResponseList<Checkin> checkins = fb.getCheckins();
			if (checkins != null && checkins.size() > 0) {
				currLocation = new Institution(checkins.get(0).getId());
			}

			// education
			List<Institution> education = new ArrayList<Institution>();
			List<Education> educations = fb.getMe().getEducation();
			if (educations != null) {
				for (Education i : educations) {
					education.add(new Institution(i.getSchool().getId()));
				}
			}

			// employer
			List<Institution> employer = new ArrayList<Institution>();
			List<Work> work = fb.getMe().getWork();
			if (work != null) {
				for (Work w : work) {
					employer.add(new Institution(w.getEmployer().getId()));

				}
			}
			
			for (Movie m : fb.getMovies()) {
				System.out.println(m.getName());
			}
		

			p = new FBPerson(firstname, name, home, location, birthdate,
					currLocation, education, employer, inter, picURL);

//			TBoSuperDuperPrinter(p);
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

	public static void TBoSuperDuperPrinter(Object p) {
		try {
			// TODO Auto-generated method stub
			for (Field field : getInheritedFields(p.getClass())) {
				field.setAccessible(true);
				String fname = field.getName();
				Object value;

				value = field.get(p);

				System.out.println("Field: " + fname + " : " + value);
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
