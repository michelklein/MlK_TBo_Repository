package de.uni.mannheim.semantic.model;


public class MatchingContainer extends AbstractToJson {

	private CelPerson celebrity;
	private CompareResult ageCompResult;
	
	public MatchingContainer(CelPerson celebrity, CompareResult ageCompResult) {
		super();
		this.celebrity = celebrity;
		this.ageCompResult = ageCompResult;
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
	
	
	
}
