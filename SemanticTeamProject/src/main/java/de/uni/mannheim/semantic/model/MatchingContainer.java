package de.uni.mannheim.semantic.model;

import java.util.List;


public class MatchingContainer extends AbstractToJson {

	private Person celebrity;
	private List<CompareResult> ageCompResult;
	private List<InterestCompareResult> movieResult;
	private List<CompareResult> locationResults;
	
	public MatchingContainer(Person celebrity, List<CompareResult> ageCompResult, List<CompareResult> locationResults,List<InterestCompareResult> movieResult) {
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
	public List<CompareResult> getAgeCompResult() {
		return ageCompResult;
	}
	public void setAgeCompResult(List<CompareResult> result) {
		this.ageCompResult = result;
	}

	public List<InterestCompareResult> getMovieResult() {
		return movieResult;
	}

	public List<CompareResult> getLocationResult() {
		return locationResults;
	}
	
	public void setMovieResult(List<InterestCompareResult> movieResult) {
		this.movieResult = movieResult;
	}
	
	
	
}
