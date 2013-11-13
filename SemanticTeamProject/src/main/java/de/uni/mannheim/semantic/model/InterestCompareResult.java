package de.uni.mannheim.semantic.model;

import java.util.List;

public class InterestCompareResult extends CompareResult {

	private static final int WIDTH_PER_IMAGE = 150;
	private String genre;
	public InterestCompareResult(int value, String genre, String desc1, String desc2,
			List<Interest> facebookIntersts, List<Interest> celebrityInterests) {
		super(value, desc1, desc2, facebookIntersts, celebrityInterests);
		this.genre = genre;
	}

	public String getHTML() {
		List<Interest> facebookIntersts = (List<Interest>) o1;
		List<Interest> celebrityInterests = (List<Interest>) o2;
		StringBuilder builder = new StringBuilder();
		builder.append("<table>")
				.append("<tr>")
				.append("<td>")
				.append(getHTMLForInterestTable(facebookIntersts))
				.append("</td><td>")
				.append("<span class=\"glyphicon glyphicon-arrow-right\" style=\"font-size:20px;float:left;color:white;\"></span>")
				.append("</td><td>")
				.append(getHTMLForInterestTable(celebrityInterests))
				.append("</td></tr>").append("</table>");
		return builder.toString();
	}

	public String getHTMLo1() {
		List<Interest> facebookIntersts = (List<Interest>) o1;
		return getHTMLForInterestTableTitle(facebookIntersts);
	}

	public String getHTMLo2() {
		List<Interest> celebrityInterests = (List<Interest>) o2;
		return getHTMLForInterestTableTitle(celebrityInterests);
	}

	private String getHTMLForInterestTableTitle(List<Interest> interests) {
		StringBuilder builder = new StringBuilder();
		builder.append("<table>");
		for (Interest interest : interests) {
			builder.append("<tr>").append("<td>")
					.append(interest.getName().replaceAll("[^\\w\\s]+", ""))
					.append("</td>").append("<td>").append("<img src=\"")
					.append(interest.getCoverURL())
					.append("\" class=\"cover_small\">").append("</td>")
					.append("</tr>");
		}
		builder.append("</table>");
		return builder.toString();
	}

	private String getHTMLForInterestTable(List<Interest> interests) {
		StringBuilder builder = new StringBuilder();
		builder.append("<table>");
		int counter = 0;
		int columns = getColumnCount(interests.size());
		boolean rowClosed = false;

		for (Interest interest : interests) {
			if (counter == 0) {
				builder.append("<tr>");
				rowClosed = false;
			}
			builder.append("<td>")
					.append("<img src=\"")
					.append(interest.getCoverURL())
					.append("\" class=\"cover_big\" style=\"margin:2px;\"></td>");
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
		return genre;
	}

	public String getO2() {
		return genre;
	}

	public String getDescription() {
		return "";
	}

	public List<Interest> getFacebookInterests() {
		return (List<Interest>) o1;
	}

	public List<Interest> getCelebrityInterests() {
		return (List<Interest>) o2;
	}

}
