package de.uni.mannheim.semantic.model;

public class InterestCompareResult extends CompareResult {

	private static final int WIDTH_PER_IMAGE = 150;
	
	public InterestCompareResult(int value, String description,
			InterestCompareObject o1, InterestCompareObject o2) {
		super(value, description, o1, o2);
	}

	public String getHTML() {
		InterestCompareObject ico1 = (InterestCompareObject) o1;

		StringBuilder builder = new StringBuilder();
		int width = (WIDTH_PER_IMAGE * ico1.getPicURLs1().size())
				+ (WIDTH_PER_IMAGE * ico1.getPicURLs2().size());
		builder.append("<div style=\"width:").append(width).append("px;\">");
		for (String picURL : ico1.getPicURLs1()) {
			builder.append("<img src=\"").append(picURL)
					.append("\" height=\"150px\" style=\"margin:2px;\">");
		}
		builder.append("<span class=\"glyphicon glyphicon-arrow-right\"></span>");
		for (String picURL : ico1.getPicURLs2()) {
			builder.append("<img src=\"").append(picURL)
					.append("\" height=\"150px\" style=\"margin:2px;\">");
		}
		builder.append("</div>");
		return builder.toString();
	}

	public String getO1() {
		return o1.toString();
	}

	public String getO2() {
		return o2.toString();
	}

}
