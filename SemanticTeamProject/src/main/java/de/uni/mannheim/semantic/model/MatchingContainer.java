package de.uni.mannheim.semantic.model;

import java.util.List;


public class MatchingContainer extends AbstractToJson {

	private CelPerson celebrity;
	private CompareResult ageCompResult;
	private CompareResult LocResult;
	private List<CompareResult> movieResult;
	
	public MatchingContainer(CelPerson celebrity, CompareResult ageCompResult, CompareResult locResult,List<CompareResult> movieResult) {
		super();
		this.celebrity = celebrity;
		this.ageCompResult = ageCompResult;
		this.LocResult = locResult;
		this.setMovieResult(movieResult);
	}
	
	public CelPerson getCelebrity() {
		return celebrity;
	}
	public void setCelebrity(CelPerson celebrity) {
		this.celebrity = celebrity;
	}
	public CompareResult getAgeCompResult() {
		return ageCompResult;
	}
	public void setAgeCompResult(CompareResult result) {
		this.ageCompResult = result;
	}

	public CompareResult getLocResult() {
		return LocResult;
	}

	public void setLocResult(CompareResult LocResult) {
		this.LocResult = LocResult;
	}

	public List<CompareResult> getMovieResult() {
		return movieResult;
	}

	public void setMovieResult(List<CompareResult> movieResult) {
		this.movieResult = movieResult;
	}
	
	
	
}
