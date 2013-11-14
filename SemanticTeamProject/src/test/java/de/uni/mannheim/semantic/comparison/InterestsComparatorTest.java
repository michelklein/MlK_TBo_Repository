package de.uni.mannheim.semantic.comparison;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import arq.rset;
import de.uni.mannheim.semantic.model.Interest;
import de.uni.mannheim.semantic.model.InterestCompareResult;

public class InterestsComparatorTest {

	private InterestsComparator comparator = new InterestsComparator();
	private static final String COVER_URL_DUMMY = "http://www.ernieputto.de/filmstuff4/batman_begins/batman_begins_poster_us2.jpg";
	
	@Test
	public void testCompare() {
		List<InterestCompareResult> results = comparator.compare(createFacebookInterests(), createCelebrityInterests());
		assertEquals(3, results.size());
		for(InterestCompareResult result : results) {
			if(result.getDescription().equals("Thriller")) {
				assertEquals(4, result.getCelebrityInterests().size());
			} else if(result.getDescription().equals("Drama")) {
				assertEquals(2, result.getCelebrityInterests().size());
			} else if(result.getDescription().equals("Crime")) {
				assertEquals(4, result.getCelebrityInterests().size());
			}
			System.out.println(String.format("%s: %s", result.getO1(), result.getValue()));
		}
	}

	private List<Interest> createFacebookInterests() {
		List<Interest> interests = new ArrayList<Interest>();
		Set<String> genres = new HashSet<String>();
		// Pain & Gain
		genres.add("Thriller");
		genres.add("Drama");
		genres.add("Comedy");
		genres.add("Crime");
		interests.add(new Interest("movie", COVER_URL_DUMMY, genres, null, "Pain & Gain",null,null));

		// Fast & Furious
		genres = new HashSet<String>();
		genres.add("Action-Thriller");
		interests.add(new Interest("movie", COVER_URL_DUMMY, genres, null,
				"Fast & Furious",null,null));

		// Kindskšpfe
		genres.add("Kšmodie");
		interests.add(new Interest("movie", COVER_URL_DUMMY, genres, null, "Kindskšpfe",null,null));

		// WHAT A MAN
		genres.add("Kšmodie");
		interests.add(new Interest("movie", COVER_URL_DUMMY, genres, null, "WHAT A MAN",null,null));
		return interests;
	}

	private List<Interest> createCelebrityInterests() {
		List<Interest> interests = new ArrayList<Interest>();
		Set<String> genres = new HashSet<String>();
		// Titan A.E.
		genres.add("Adventure");
		genres.add("Action");
		genres.add("Animation");
		genres.add("Sci-Fi");
		genres.add("Family");
		interests.add(new Interest("movie", COVER_URL_DUMMY, genres, null, "Titan A.E.",null,null));

		// School Ties
		genres = new HashSet<String>();
		genres.add("Drama");
		interests.add(new Interest("movie", COVER_URL_DUMMY, genres, null, "School Ties",null,null));

		// Ocean's Twelve.
		genres = new HashSet<String>();
		genres.add("Crime");
		genres.add("Thriller");
		interests.add(new Interest("movie", COVER_URL_DUMMY, genres, null,
				"Ocean's Twelve",null,null));

		// The Rainmaker
		genres = new HashSet<String>();
		genres.add("Crime");
		genres.add("Thriller");
		interests
				.add(new Interest("movie", COVER_URL_DUMMY, genres, null, "The Rainmaker",null,null));

		// The Talented Mr. Ripley
		genres = new HashSet<String>();
		genres.add("Crime");
		genres.add("Thriller");
		genres.add("Music");
		genres.add("Drama");
		interests.add(new Interest("movie", COVER_URL_DUMMY, genres, null,
				"The Talented Mr. Ripley",null,null));

		// The Bourne Ultimatum
		genres = new HashSet<String>();
		genres.add("Crime");
		genres.add("Thriller");
		genres.add("Action");
		interests.add(new Interest("movie", COVER_URL_DUMMY, genres, null,
				"The Bourne Ultimatum",null,null));

		return interests;
	}

}
