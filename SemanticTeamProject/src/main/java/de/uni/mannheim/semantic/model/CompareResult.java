package de.uni.mannheim.semantic.model;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.sparql.algebra.OpAsQuery.SubExprForVar;

public class CompareResult extends AbstractToJson {

	/** The achieved result in percent */
	private int value = 0;
	/** The description describing the achieved result */
	private String description;
	/** Indicates if the compare result is valid or not. */
	private boolean valid = true;

	/** Maybe this result consists of subresults */
	private List<CompareResult> subresults;

	public CompareResult(int value, String description) {
		super();
		this.value = value;
		this.description = description;
	}

	public CompareResult(int value, String description, boolean valid) {
		super();
		this.value = value;
		this.description = description;
		this.valid = valid;
	}

	public CompareResult() {
	}

	public List<CompareResult> getSubresults() {
		if (subresults == null) {
			subresults = new ArrayList<CompareResult>();
		}
		return subresults;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isValid() {
		return valid;
	}

	public int getSum() {

		int sum = 0;
		for (CompareResult sr : subresults) {
			sum = sum + sr.getValue();
		}
		return sum;

	}

	public String getHTML() {
		StringBuilder builder = new StringBuilder();
		builder.append("<table border=\"0\" style=\"color:white;\">");
		String value = "-";
		for (CompareResult rs : getSubresults()) {
			if (rs.isValid()) {
				value = rs.getValue() + "%";
			}
			builder.append("<tr>").append("<td><li>")
					.append("</li></td><td style=\"padding-right: 10px;\">")
					.append(rs.getDescription())
					.append(":</td><td style=\"text-align: right;\">")
					.append(value).append("</td></tr>");
		}
		builder.append("<tr>").append("<td style=\"padding-top: 10px;\"><li>")
				.append("</li></td><td style=\"padding-top: 10px;\"><b>")
				.append(getDescription())
				.append(":</b></td><td style=\"padding-top: 10px;\"><b>")
				.append(getValue()).append("%</b></td></tr>");
		return builder.toString();
	}

}
