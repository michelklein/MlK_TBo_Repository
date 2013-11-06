package de.uni.mannheim.semantic.model;

import java.util.List;

public class InterestCompareResult extends CompareResult {

	public InterestCompareResult(int value, String description, Object o1, Object o2) {
		super(value, description, o1,o2);
	}

	public String getHTML() {
		List<Interest> interests1 = (List<Interest>) o1;
		List<Interest> interests2 = (List<Interest>) o2;
		StringBuilder builder = new StringBuilder();
		for(Interest i : interests1) {
			builder.append("<img src='").append(i.getCoverURL()).append("' height=\"150px\">");
		}
		builder.append("<span class=\"glyphicon glyphicon-arrow-right\"></span>");
		for(Interest i : interests2) {
			builder.append("<img src='").append(i.getCoverURL()).append("' height=\"150px\">");
		}
		return builder.toString();
	}
	
}
