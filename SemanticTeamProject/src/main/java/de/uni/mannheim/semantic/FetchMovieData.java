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

import de.uni.mannheim.semantic.model.Interest;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONObject;

public class FetchMovieData {

	private static final String API_URL = "http://mymovieapi.com/";
	private static final Pattern pattern = Pattern.compile("\\d{4}");
	
	public Interest getMovie(String title) {
		return getMovie(title, null);
	}

	public Interest getMovie(String title, String year) {
		if (title == null) {
			return null;
		}

		Matcher matcher = pattern.matcher(year);
		if (year != null && matcher.find()) {
			int endIndex = matcher.end();
			String convertedYear = year.substring(endIndex - 4, endIndex);
			System.out.println(String.format("Convert Year: %s to %s", year, convertedYear));
			year = convertedYear;
		}

		try {
			String line;
			String result = "";
			URL url = getURL(title, year);
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
					System.out.println(json.get("error") + " for movie: " + title);
					return null;
				}
			}
			// convert result to json object
			JSONArray jsonArray = new JSONArray(result);
			JSONObject json = (JSONObject) jsonArray.get(0);
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
			return new Interest("movie", imageURL, genres, null, title);
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return url;
	}

	private String convertTitleForURL(String title) {
		return URLEncoder.encode(title);
	}

}
