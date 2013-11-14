package de.uni.mannheim.semantic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import de.uni.mannheim.semantic.model.Interest;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONObject;

public class FetchMovieData {
	private Logger logger = LogManager.getLogger(FetchMovieData.class
			.getName());
	private static final String API_URL = "http://mymovieapi.com/";
	private static final Pattern pattern = Pattern.compile("\\d{4}");

	public Interest getMovieByIMDBId(String id) {
		String imdbId = id.substring(id.lastIndexOf("/") + 1);
		logger.info("load movie with imdbid: " + imdbId);
		return getMovieResult(getURL(imdbId));
	}

	public Interest getMovie(String title) {
		return getMovie(title, null);
	}

	public Interest getMovie(String title, String year) {
		logger.info(String.format("load movie with title: %s and year: %s", title, year));
		if (title == null) {
			logger.warn("title should not be null");
			return null;
		}

		if (year != null) {
			Matcher matcher = pattern.matcher(year);
			if (matcher.find() && year.length() > 4) {
				int endIndex = matcher.end();
				String convertedYear = year.substring(endIndex - 4, endIndex);
				logger.debug(String.format("Convert Year: %s to %s",
						year, convertedYear));
				year = convertedYear;
			}
		}
		URL url = getURL(title, year);
		return getMovieResult(url);
	}

	private Interest getMovieResult(URL url) {
		try {
			String line;
			String result = "";
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
			if (result.contains("error")) {
				JSONObject json = new JSONObject(result);
				if (json.has("code")) {
					System.out
							.println(json.get("error") + " for movie: " + url);
					return null;
				}
			}
			// convert result to json object
			JSONObject json = null;
			if (result.startsWith("[")) {
				JSONArray jsonArray = new JSONArray(result);
				json = (JSONObject) jsonArray.get(0);
			} else {
				json = new JSONObject(result);
			}
			if (!json.has("poster")) {
				return null;
			}
			JSONObject poster = json.getJSONObject("poster");
			String imageURL = null;
			if (poster != null) {
				imageURL = poster.getString("cover");
			}
			Set<String> genres = null;
			JSONArray genreArray = json.getJSONArray("genres");
			if (genreArray != null) {
				genres = new HashSet<String>(genreArray.length());
				for (int i = 0; i < genreArray.length(); i++) {
					genres.add(genreArray.getString(i));
				}
			}

			String title = json.getString("title");
			String location = null;
			if (json.has("filming_locations")) {
				location = json.getString("filming_locations");
			}
			Interest movie = new Interest("movie", imageURL, genres, null, title, location);
			logger.info(String.format("found movie: %s", movie.toString()));
			return movie;
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return null;
	}

	private URL getURL(String title, String year) {
		URL url = null;
		try {
			if (year != null) {
				url = new URL(
						String.format(
								"%s?title=%s&year=%s&type=json&plot=simple&episode=1&limit=1&yg=0&mt=none&lang=en-US&offset=&aka=simple&release=simple&business=0&tech=0",
								API_URL, convertTitleForURL(title), year));
			} else {
				url = new URL(
						String.format(
								"%s?title=%s&type=json&plot=simple&episode=1&limit=1&yg=1&mt=none&lang=en-US&offset=&aka=simple&release=simple&business=0&tech=0",
								API_URL, convertTitleForURL(title)));
			}
		} catch (MalformedURLException e) {
			logger.error(e.toString(), e);
		}
		return url;
	}

	private URL getURL(String imdbId) {
		URL url = null;
		try {
			url = new URL(
					String.format(
							"%s?id=%s&type=json&plot=simple&episode=1&lang=en-US&aka=simple&release=simple&business=0&tech=0",
							API_URL, imdbId));
		} catch (MalformedURLException e) {
			logger.error(e.toString(), e);
		}
		return url;
	}

	private String convertTitleForURL(String title) {
		return URLEncoder.encode(title);
	}

}
