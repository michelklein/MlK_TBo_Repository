package de.uni.mannheim.semantic.model;

import java.util.ArrayList;
import java.util.List;

public class MatchingContainer extends AbstractToJson {

	private Person celebrity;
	private List<CompareResult> ageCompResult;
	private List<InterestCompareResult> movieResult;
	private List<CompareResult> locationResults;
	private int total = 0;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public MatchingContainer(Person celebrity,
			List<CompareResult> ageCompResult,
			List<CompareResult> locationResults,
			List<InterestCompareResult> movieResult) {
		super();
		this.celebrity = celebrity;
		this.ageCompResult = ageCompResult;
		this.locationResults = locationResults;
		this.movieResult = movieResult;

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

	public void calcTotal() {
		if (this.celebrity != null && this.locationResults != null
				&& this.movieResult != null && this.ageCompResult != null) {
			List<CompareResult> allRes = new ArrayList<CompareResult>();
			allRes.addAll(this.ageCompResult);
			allRes.addAll(this.locationResults);
			allRes.addAll(this.movieResult);

			for (CompareResult cr : allRes) {
				System.out.println(cr.getO1() + ":" + cr.getSum());
				total += cr.getSum();

			}
			total = total / allRes.size();
		}
	}

	public void setLocationResults(List<CompareResult> locationResults) {
		// TODO Auto-generated method stub
		this.locationResults = locationResults;
	}

}
