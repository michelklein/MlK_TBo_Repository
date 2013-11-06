package de.uni.mannheim.semantic.model;

import java.util.List;


public class MatchingContainer extends AbstractToJson {

	private Person celebrity;
	private CompareResult ageCompResult;
	private List<CompareResult> movieResult;
	private List<CompareResult> locationResults;
	
	public MatchingContainer(Person celebrity, CompareResult ageCompResult, List<CompareResult> locationResults,List<CompareResult> movieResult) {
		super();
		this.celebrity = celebrity;
		this.ageCompResult = ageCompResult;
		this.locationResults = locationResults;
		this.setMovieResult(movieResult);
	}
	
	public Person getCelebrity() {
		return celebrity;
	}
	public void setCelebrity(Person celebrity) {
		this.celebrity = celebrity;
	}
	public CompareResult getAgeCompResult() {
		return ageCompResult;
	}
	public void setAgeCompResult(CompareResult result) {
		this.ageCompResult = result;
	}

	public List<CompareResult> getMovieResult() {
		return movieResult;
	}

	public List<CompareResult> getLocationResult() {
		return locationResults;
	}
	
	public void setMovieResult(List<CompareResult> movieResult) {
		this.movieResult = movieResult;
	}
	
	
	
}
