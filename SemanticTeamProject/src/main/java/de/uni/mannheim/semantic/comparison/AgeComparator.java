package de.uni.mannheim.semantic.comparison;

import java.util.Calendar;
import java.util.Date;

import de.uni.mannheim.semantic.model.CompareResult;

public class AgeComparator extends AbstractComparator<Date> {

	private static final int MILLENNIUM_PERCENT = 5;
	private static final int YEAR_PERCENT = 20;
	private static final int MONTH_PERCENT = 15;
	private static final int WEEK_OF_YEAR_PERCENT = 20;
	private static final int DAY_OF_WEEK_PERCENT = 10;
	private static final int DAY_PERCENT = 30;
	
	@Override
	public CompareResult compare(Date o1, Date o2) {
		CompareResult result = new CompareResult();
		result.setDescription("Overall");
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(o1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(o2);

		// millennium comparison
		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		String millenium1 = String.valueOf(year1).substring(0, 3);
		String millenium2 = String.valueOf(year2).substring(0, 3);
		int tempResult = millenium1.equals(millenium2) ?  MILLENNIUM_PERCENT : 0;
		result.getSubrestults().add(new CompareResult(tempResult,"Millennium"));
		result.setValue(result.getValue()+tempResult);
		
		// year comparison
		compareHelper(year1, year2, YEAR_PERCENT, "Year", result);
		// month comparison
		compareHelper(cal1.get(Calendar.MONTH), cal2.get(Calendar.MONTH), MONTH_PERCENT, "Month", result);
		// week of year comparison
		compareHelper(cal1.get(Calendar.WEEK_OF_YEAR), cal2.get(Calendar.WEEK_OF_YEAR), WEEK_OF_YEAR_PERCENT, "Week of Year", result);
		// day of week comparison
		compareHelper(cal1.get(Calendar.DAY_OF_WEEK), cal2.get(Calendar.DAY_OF_WEEK), DAY_OF_WEEK_PERCENT, "Day of Week", result);
		// day comparison
		compareHelper(cal1.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH), DAY_PERCENT, "Day", result);

		return result;
	}

	private void compareHelper(int val1, int val2, int percent, String description, CompareResult result) {
		int tempResult = val1 == val2 ?  percent : 0;
		result.setValue(result.getValue()+tempResult);
		result.getSubrestults().add(new CompareResult(tempResult, description));
	}
	

}
