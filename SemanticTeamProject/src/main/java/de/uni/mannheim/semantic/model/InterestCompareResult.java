package de.uni.mannheim.semantic.model;

import java.util.List;

public class InterestCompareResult extends CompareResult {

	private static final int WIDTH_PER_IMAGE = 150;

	public InterestCompareResult(int value, String description,
			List<Interest> facebookIntersts, List<Interest> celebrityInterests) {
		super(value, description, facebookIntersts, celebrityInterests);
	}

	public String getHTML3() {
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

	public String getHTML() {
		List<Interest> facebookIntersts = (List<Interest>) o1;
		List<Interest> celebrityInterests = (List<Interest>) o2;
		StringBuilder builder = new StringBuilder();
		builder.append("<div>").append(getHTMLForInterestTable(facebookIntersts))
				.append("<span class=\"glyphicon glyphicon-arrow-right\" style=\"font-size:20px;float:left;\"></span>")
				.append(getHTMLForInterestTable(celebrityInterests))
				.append("</div>");
		return builder.toString();
	}

	private String getHTMLForInterestTable(List<Interest> interests) {
		StringBuilder builder = new StringBuilder();
		builder.append("<table border=\"0\" style=\"color:white;float:left;\">");
		int counter = 0;
		int columns = getColumnCount(interests.size());
		boolean rowClosed = false;

		for (Interest interest : interests) {
			if (counter == 0) {
				builder.append("<tr>");
				rowClosed = false;
			}
			builder.append("<td>").append("<img src=\"")
					.append(interest.getCoverURL())
					.append("\" height=\"150px\" style=\"margin:2px;\"></td>");
			counter++;
			if (counter == columns) {
				counter = 0;
				builder.append("</tr>");
				rowClosed = true;
			}
		}
		if (!rowClosed) {
			builder.append("</tr>");
		}
		builder.append("</table>");
		return builder.toString();
	}

	private int getColumnCount(int size) {
		if (size == 1) {
			return 1;
		} else if (size <= 4) {
			return 2;
		} else if (size <= 9) {
			return 3;
		} else {
			return 4;
		}
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
