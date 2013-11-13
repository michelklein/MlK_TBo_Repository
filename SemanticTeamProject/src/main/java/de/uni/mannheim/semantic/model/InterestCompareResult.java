package de.uni.mannheim.semantic.model;

import java.util.List;

public class InterestCompareResult extends CompareResult {

	private static final int WIDTH_PER_IMAGE = 150;
	
	public InterestCompareResult(int value, String description, 
			List<Interest> facebookIntersts, List<Interest> celebrityInterests) {
		super(value, description, facebookIntersts, celebrityInterests);
	}

	public String getHTML() {
		List<Interest> facebookIntersts = (List<Interest>) o1;
		List<Interest> celebrityInterests = (List<Interest>) o2;

		StringBuilder builder = new StringBuilder();
		int width = (WIDTH_PER_IMAGE * facebookIntersts.size())
				+ (WIDTH_PER_IMAGE * celebrityInterests.size());
		builder.append("<div style=\"width:").append(width).append("px;\">");
		for (Interest interst : facebookIntersts) {
			builder.append("<img src=\"").append(interst.getCoverURL())
			.append("\" height=\"150px\" style=\"margin:2px;\">");
		}
		builder.append("<span class=\"glyphicon glyphicon-arrow-right\"></span>");
		for (Interest interst : celebrityInterests) {
			builder.append("<img src=\"").append(interst.getCoverURL())
					.append("\" height=\"150px\" style=\"margin:2px;\">");
		}
		builder.append("</div>");
		return builder.toString();
	}

	public String getO1() {
		return description;
	}

	public String getO2() {
		return description;
	}
	
	public List<Interest> getFacebookInterests() {
		return (List<Interest>) o1;
	}
	
	public List<Interest> getCelebrityInterests() {
		return (List<Interest>) o2;
	}

}
