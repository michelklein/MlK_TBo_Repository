package de.uni.mannheim.semantic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all information about to compared object. These information are used to create the progress bars in the ui.
 * 
 * @author Michel
 */
public class CompareResult extends AbstractToJson {

	/** The achieved result in percent */
	protected int value = 0;
	/** The description describing the achieved result */
	protected String desc1;
	protected String desc2;
	
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}
	/** Indicates if the compare result is valid or not. */
	protected boolean valid = true;

	protected Object o1;
	protected Object o2;

	/** Maybe this result consists of subresults */
	private List<CompareResult> subresults;

	public CompareResult(int value, String desc1,String desc2, Object o1, Object o2) {
		super();
		this.value = value;
		this.desc1 = desc1;
		this.desc2 = desc2;
		this.o1 = o1;
		this.o2 = o2;
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

	public String getDesc1() {
		return desc1;
	}
	
	public String getDesc2() {
		return desc2;
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
	
	public String getHTMLo1() {
		AbstractToolTipInfo facebook = (AbstractToolTipInfo) o1;
		return getHTMLForToolTipInfo(facebook);
	}

	public String getHTMLo2() {
		AbstractToolTipInfo celebrity = (AbstractToolTipInfo) o2;
		return getHTMLForToolTipInfo(celebrity);
	}

	private String getHTMLForToolTipInfo(AbstractToolTipInfo toolTip) {
		if(toolTip == null || (toolTip.getToolTipInfo() == null || toolTip.getToolTipInfo().equals(""))) {
			return null;
		}
		return "<div>"+toolTip.getToolTipInfo()+"</div>";
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
					.append(rs.getDesc1())
					.append(":</td><td style=\"text-align: right;\">")
					.append(value).append("</td></tr>");
		}
		builder.append("<tr>").append("<td style=\"padding-top: 10px;\"><li>")
				.append("</li></td><td style=\"padding-top: 10px;\"><b>")
				.append("Total:</b></td><td style=\"padding-top: 10px;\"><b>")
				.append(getValue()).append("%</b></td></tr>");
		return builder.toString();
	}
	
	public String getO1(){
		return o1.toString();
	}
	public String getO2(){
		return o2.toString();
	}
}
