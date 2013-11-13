package de.uni.mannheim.semantic;

import static org.junit.Assert.*;

import org.junit.Test;

import de.uni.mannheim.semantic.model.Interest;

public class FetchMovieDataTest {

	private FetchMovieData movieFetcher = new FetchMovieData();
	
	@Test
	public void testGetMovie() {
		Interest painGain = movieFetcher.getMovie("Pain & Gain");
		assertNotNull(painGain);
		assertNotNull(painGain.getGenre());
		assertNotNull(painGain.getCoverURL());
		
		painGain = movieFetcher.getMovie("Pain & Gain", "2013");
		assertNotNull(painGain);
		assertNotNull(painGain.getGenre());
		assertNotNull(painGain.getCoverURL());
		
		painGain = movieFetcher.getMovie(null);
		assertNull(painGain);
		
		painGain = movieFetcher.getMovie(null, "2013");
		assertNull(painGain);
		
		painGain = movieFetcher.getMovie(null, null);
		assertNull(painGain);
		
		painGain = movieFetcher.getMovieByIMDBId("http://www.imdb.com/title/tt0054189");
		assertNotNull(painGain);
		
	}

}
