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
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import de.uni.mannheim.semantic.FetchGeoData;
import de.uni.mannheim.semantic.FetchMovieData;
import de.uni.mannheim.semantic.jena.CelebritiesFetcher;
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

	private Logger logger = LogManager
			.getLogger(FacebookParser.class.getName());
	private Facebook fb;
	private FetchGeoData geocoding = new FetchGeoData();
	private FetchMovieData movieFetcher = new FetchMovieData();
	private Pattern pattern = Pattern.compile("[-/{}()]");

	public FacebookParser(Facebook f) {
		fb = f;
	}

	public Person parseFacebookPerson() {
		logger.info("Starting parse facebook person");
		Person person = null;
		Set<Location> locations = new HashSet<Location>();
		try {
			// Picture
			String picURL = getLargeProfilePicture();
			// First name
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
			// allInts.addAll(fb.getBooks());
			allInts.addAll(fb.getMovies());
			// allInts.addAll(fb.getTelevision());
			// allInts.addAll(fb.getMusic());
			// allInts.addAll(fb.getGames());

			// fb.getLikedPage();

			for (Category c : allInts) {
				Interest interest = createInterestByID(c);
				if (interest != null)
					interests.add(interest);
				// TBoSuperDuperPrinter(createInterestByID(c));
			}

			for (Interest i : interests) {
				i.getGenre().addAll(
						CelebritiesFetcher.get().getGenreFromFile(i.getName()));
			}
			person = new Person(firstname, name, birthdate, locations,
					interests, picURL);
		} catch (FacebookException e) {
			logger.error(e.toString(), e);
		} catch (ParseException e) {
			logger.error(e.toString(), e);
		} catch (IllegalArgumentException e) {
			logger.error(e.toString(), e);
		}
		logger.info("Finish pare facebook person: " + person.toString());
		return person;
	}

	private Interest createInterestByID(Category c) {
		logger.info("load interest by category: " + c.getName());
		JSONArray fqlRes;
		try {
			fqlRes = fb
					.executeFQL("SELECT pic_cover.source,genre,release_date  FROM page  WHERE page_id='"
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
				String name = c.getName();
				String releaseDate = jo.getString("release_date");
				if (!releaseDate.equals(""))
					name = name
							+ " ("
							+ releaseDate.substring(releaseDate
									.lastIndexOf("/") + 1) + ")";
				if ("Movie".equalsIgnoreCase(c.getCategory())) {
					// get year from release date
					Interest movie = movieFetcher.getMovie(c.getName(),
							releaseDate);
					// replace last part seperated with space from name
					if (movie == null && pattern.matcher(c.getName()).find()) {
						String[] split = c.getName().split(" ");
						StringBuilder builder = new StringBuilder();
						for (int i = 0; i < split.length - 1; i++) {
							builder.append(split[i]).append(" ");
						}
						movie = movieFetcher.getMovie(builder.toString(),
								releaseDate);
					}
					logger.info(String.format("found movie: %s", movie));
					return movie;
				}
				Interest interest = new Interest(c.getCategory(), url, genre, c.getId(),
						name, null);
				logger.info(String.format("found interest: %s", interest.toString()));
				return interest;
			}

		} catch (FacebookException e) {
			logger.error(e.toString(), e);
		} catch (JSONException e) {
			logger.error(e.toString(), e);
		}
		logger.warn("no interest found");
		return null;

	}

	private String getLargeProfilePicture() {
		logger.info("load large profile picture");
		JSONArray fqlRes;
		try {
			fqlRes = fb
					.executeFQL("SELECT pid, object_id, src_big FROM photo WHERE object_id IN (SELECT cover_object_id FROM album WHERE owner=me() AND type='profile')");

			if (fqlRes.length() == 1) {
				Object g = fqlRes.getJSONObject(0).get("src_big");
				return g.toString();
			}

		} catch (FacebookException e) {
			logger.error(e.toString(), e);
		} catch (JSONException e) {
			logger.error(e.toString(), e);
		}
		logger.info("no large profile picture found!");
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
			logger.error(e.toString(), e);
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
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
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
