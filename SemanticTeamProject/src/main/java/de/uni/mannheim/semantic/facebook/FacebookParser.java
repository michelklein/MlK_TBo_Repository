package de.uni.mannheim.semantic.facebook;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.uni.mannheim.semantic.FetchGeoData;
import de.uni.mannheim.semantic.model.Interest;
import de.uni.mannheim.semantic.model.Location;
import de.uni.mannheim.semantic.model.Person;
import facebook4j.Category;
import facebook4j.Checkin;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.IdNameEntity;
import facebook4j.Page;
import facebook4j.ResponseList;
import facebook4j.User.Education;
import facebook4j.User.Work;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

public class FacebookParser {
	private Facebook fb;
	private FetchGeoData geocoding = new FetchGeoData();

	public FacebookParser(Facebook f) {
		fb = f;
	}

	public Person parseFacebookPerson() {
		Person person = null;
		Set<Location> locations = new HashSet<Location>();
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
				Location loc = parseLocation(hometown.getId(),
						Location.BIRTHPLACE);
				if (loc != null) {
					locations.add(loc);
				}
			}

			IdNameEntity locationTmp = fb.getMe().getLocation();
			if (locationTmp != null) {
				Location loc = parseLocation(locationTmp.getId(),
						Location.CURRENT_LOCATION);
				if (loc != null) {
					locations.add(loc);
				}
			}

			// Interest
			List<String> inter = fb.getMe().getInterestedIn();
			// Birthdate
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date birthdate = df.parse(fb.getMe().getBirthday());

			// CurrLocation
			ResponseList<Checkin> checkins = fb.getCheckins();
			if (checkins != null && checkins.size() > 0) {
				Location loc = parseLocation(checkins.get(0).getId(),
						Location.CURRENT_LOCATION);
				if (loc != null) {
					locations.add(loc);
				}
			}

			// education
			List<Education> educations = fb.getMe().getEducation();
			if (educations != null) {
				for (Education i : educations) {
					if (locationTmp != null) {
						Location loc = parseLocation(i.getSchool().getId(),
								Location.EDUCATIONPLACE);
						if (loc != null) {
							locations.add(loc);
						}
					}
				}
			}

			// employer
			List<Work> work = fb.getMe().getWork();
			if (work != null) {
				for (Work w : work) {
					if (locationTmp != null) {
						Location loc = parseLocation(w.getEmployer().getId(),
								Location.WORKPLACE);
						if (loc != null) {
							locations.add(loc);
						}
					}
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
			person = new Person(firstname, name, birthdate, locations,
					interests, picURL);
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

	private Location parseLocation(String id, String description) {
		Page p;
		try {
			p = fb.getPage(id);
			facebook4j.Place.Location location = p.getLocation();
			if (location != null) {
				Location loc = geocoding.getLocation(location.getLongitude(),
						location.getLatitude(), description);
				if (loc == null) {
					loc = geocoding
							.getLocation(location.getCity(), description);
				}
				return loc;
			}
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// public static void TBoSuperDuperPrinter(Object p) {
	// try {
	// for (Field field : getInheritedFields(p.getClass())) {
	// field.setAccessible(true);
	// String fname = field.getName();
	// Object value;
	//
	// value = field.get(p);
	//
	// System.out.println("Field: " + fname + " : " + value);
	//
	// }
	// } catch (IllegalArgumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// public static List<Field> getInheritedFields(Class<?> type) {
	// List<Field> fields = new ArrayList<Field>();
	// for (Class<?> c = type; c != null; c = c.getSuperclass()) {
	// fields.addAll(Arrays.asList(c.getDeclaredFields()));
	// }
	// return fields;
	// }

}
