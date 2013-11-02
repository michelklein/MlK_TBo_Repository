package de.uni.mannheim.semantic.model;


public class MatchingContainer extends AbstractToJson {

	private CelPerson celebrity;
	private CompareResult ageCompResult;
	private CompareResult hometownResult;
	
	public MatchingContainer(CelPerson celebrity, CompareResult ageCompResult, CompareResult hometownResult) {
		super();
		this.celebrity = celebrity;
		this.ageCompResult = ageCompResult;
		this.hometownResult = hometownResult;
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

	public CompareResult getHometownResult() {
		return hometownResult;
	}

	public void setHometownResult(CompareResult hometownResult) {
		this.hometownResult = hometownResult;
	}
	
	
	
}
