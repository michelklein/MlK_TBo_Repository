package de.uni.mannheim.semantic.comparison;

import java.util.Calendar;
import java.util.Date;

import de.uni.mannheim.semantic.model.CompareResult;

public class AgeComparator extends AbstractComparator<Date> {

        private static final int DECADE_PERCENT = 5;
        private static final int CENTURY_PERCENT = 5;
        private static final int YEAR_PERCENT = 5;
        private static final int MONTH_PERCENT = 10;
        private static final int WEEK_OF_YEAR_PERCENT = 5;
        private static final int DAY_OF_WEEK_PERCENT = 10;
        private static final int DAY_PERCENT = 10;
        private static final int AGE_PERCENT = 50;

        @Override
        public CompareResult compare(Date o1, Date o2) {
                this.o1 = o1;
                this.o2 = o2;
            	result = new CompareResult(0, "Overall", o1, o2);
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(o1);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(o2);

                // age comparison
                int age1 = getAge(cal1);
                int age2 = getAge(cal2);
                int div = age1 - age2;
                div = div < 0 ? div * -1 : div;
                if (age1 > 100 || age2 > 100) {
                        result.getSubresults().add(new CompareResult(0, "Age",o1,o2));
                } else {
                        double divide = (double) (100 - div) / 100;
                        double agePercent = AGE_PERCENT * divide;
                        result.getSubresults().add(
                                        new CompareResult((int) agePercent, "Age",o1,o2));
                        result.setValue((int) (result.getValue() + agePercent));
                }
                // century comparison
                int year1 = cal1.get(Calendar.YEAR);
                int year2 = cal2.get(Calendar.YEAR);
                String century1 = String.valueOf(year1).substring(0, 2);
                String century2 = String.valueOf(year2).substring(0, 2);
                int tempResult = century1.equals(century2) ? CENTURY_PERCENT : 0;
                result.getSubresults().add(new CompareResult(tempResult, "Century",o1,o2));
                result.setValue(result.getValue() + tempResult);

                // decade comparison
                String decade1 = String.valueOf(year1).substring(0, 3);
                String decade2 = String.valueOf(year2).substring(0, 3);
                tempResult = decade1.equals(decade2) ? DECADE_PERCENT : 0;
                result.getSubresults().add(new CompareResult(tempResult, "Decade",o1,o2));
                result.setValue(result.getValue() + tempResult);

                // year comparison
                compareHelper(year1, year2, YEAR_PERCENT, "Year", result);
                // month comparison
                compareHelper(cal1.get(Calendar.MONTH), cal2.get(Calendar.MONTH),
                                MONTH_PERCENT, "Month", result);
                // week of year comparison
                compareHelper(cal1.get(Calendar.WEEK_OF_YEAR),
                                cal2.get(Calendar.WEEK_OF_YEAR), WEEK_OF_YEAR_PERCENT,
                                "Week of Year", result);
                // day of week comparison
                compareHelper(cal1.get(Calendar.DAY_OF_WEEK),
                                cal2.get(Calendar.DAY_OF_WEEK), DAY_OF_WEEK_PERCENT,
                                "Day of Week", result);
                // day comparison
                compareHelper(cal1.get(Calendar.DAY_OF_MONTH),
                                cal2.get(Calendar.DAY_OF_MONTH), DAY_PERCENT, "Day", result);

                return result;
        }

        private int getAge(Calendar dob) {
                Calendar today = Calendar.getInstance();
                int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
                if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
                        age--;
                } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
                                && today.get(Calendar.DAY_OF_MONTH) < dob
                                                .get(Calendar.DAY_OF_MONTH)) {
                        age--;
                }
                return age;
        }

}