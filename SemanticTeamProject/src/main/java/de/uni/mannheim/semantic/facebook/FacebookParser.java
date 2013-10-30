package de.uni.mannheim.semantic.facebook;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.jena.atlas.json.JSON;
import org.apache.jena.atlas.lib.ListUtils;

import de.uni.mannheim.semantic.model.FacebookPerson;
import de.uni.mannheim.semantic.model.Institution;
import de.uni.mannheim.semantic.model.Interest;
import de.uni.mannheim.semantic.model.Location;
import facebook4j.Book;
import facebook4j.Category;
import facebook4j.Checkin;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Game;
import facebook4j.IdNameEntity;
import facebook4j.Movie;
import facebook4j.Music;
import facebook4j.Page;
import facebook4j.PictureSize;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.Television;
import facebook4j.User;
import facebook4j.User.Education;
import facebook4j.User.Work;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

public class FacebookParser {
	private Facebook fb;

	public FacebookParser(Facebook f) {
		fb = f;
	}

	public FacebookPerson parseFacebookPerson() {
		Institution home = null;
		Institution location = null;
		Institution currLocation = null;
		FacebookPerson person = null;

		try {
			// Picture
			// String picURL = fb.getPictureURL(PictureSize.large).toString();
			String picURL = getLargeProfilePicture();
			// Firstname
			String firstname = fb.getMe().getFirstName();
			// Name
			String name = fb.getMe().getLastName();
			// Home
			IdNameEntity hometown = fb.getMe().getHometown();
			if (hometown != null) {
				home = parseInstitution(hometown.getId());
			}

			IdNameEntity locationTmp = fb.getMe().getLocation();
			if (locationTmp != null) {
				location = parseInstitution(locationTmp.getId());
			}

			// Interest
			List<String> inter = fb.getMe().getInterestedIn();
			// Birthdate
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date birthdate = df.parse(fb.getMe().getBirthday());

			// CurrLocation
			ResponseList<Checkin> checkins = fb.getCheckins();
			if (checkins != null && checkins.size() > 0) {
				currLocation = parseInstitution(checkins.get(0).getId());
			}

			// education
			List<Institution> education = new ArrayList<Institution>();
			List<Education> educations = fb.getMe().getEducation();
			if (educations != null) {
				for (Education i : educations) {
					education.add(parseInstitution(i.getSchool().getId()));
				}
			}

			// employer
			List<Institution> employer = new ArrayList<Institution>();
			List<Work> work = fb.getMe().getWork();
			if (work != null) {
				for (Work w : work) {
					employer.add(parseInstitution(w.getEmployer().getId()));

				}
			}

			List<Interest> interests = new ArrayList<Interest>();

			List<Category> allInts = new ArrayList<Category>();
			allInts.addAll(fb.getBooks());
			allInts.addAll(fb.getMovies());
			allInts.addAll(fb.getTelevision());
			allInts.addAll(fb.getMusic());
			allInts.addAll(fb.getGames());

			for (Category c : allInts) {
				interests.add(createInterestByID(c));
				// TBoSuperDuperPrinter(createInterestByID(c));
			}
			person = new FacebookPerson(firstname, name, home, location,
					birthdate, currLocation, education, employer, interests,
					picURL);
			// TBoSuperDuperPrinter(person);
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return person;

	}

	private Interest createInterestByID(Category c) {
		// TODO Auto-generated method stub
		JSONArray fqlRes;
		try {
			fqlRes = fb
					.executeFQL("SELECT pic_cover.source,genre  FROM page  WHERE page_id='"
							+ c.getId() + "'");
			if (fqlRes.length() == 1) {

				JSONObject jo = fqlRes.getJSONObject(0);
				String url = "";
				if (jo.getString("pic_cover") != null) {
					JSONObject pc = jo.getJSONObject("pic_cover");
					url = pc.getString("source");
				}

				Set<String> genre = new HashSet<String>();
				genre.addAll(Arrays.asList(jo.getString("genre").split(",")));
				return new Interest(c.getCategory(), url, genre, c.getId(),
						c.getName());
			}

		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private String getLargeProfilePicture() {

		JSONArray fqlRes;
		try {
			fqlRes = fb
					.executeFQL("SELECT pid, object_id, src_big FROM photo WHERE object_id IN (SELECT cover_object_id FROM album WHERE owner=me() AND type='profile')");

			if (fqlRes.length() == 1) {
				Object g = fqlRes.getJSONObject(0).get("src_big");
				return g.toString();
			}

		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private Institution parseInstitution(String id) {
		Page p;
		try {
			p = fb.getPage(id);
			String name = p.getName();
			facebook4j.Place.Location location = p.getLocation();
			if (location != null) {
				return new Institution(name, location.getLongitude(),
						location.getLatitude());
			}
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void TBoSuperDuperPrinter(Object p) {
		try {
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

}
