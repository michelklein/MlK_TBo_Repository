package de.uni.mannheim.semantic.comparison;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import de.uni.mannheim.semantic.model.CompareResult;

public class AgeComparatorTest {
	private DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	private AgeComparator ageComparator = new AgeComparator();

	@Test
	public void testCompareDateDate() throws ParseException {
		Date d1 = df.parse("12.01.1989");
		Date d2 = df.parse("12.01.1991");
		CompareResult result = ageComparator.compare(d1, d2);
		print(result, d1, d2);

		d1 = df.parse("12.01.1989");
		d2 = df.parse("17.09.1989");
		result = ageComparator.compare(d1, d2);
		print(result, d1, d2);
		
		
		d1 = df.parse("12.01.1989");
		d2 = df.parse("02.02.1993");
		result = ageComparator.compare(d1, d2);
		print(result, d1, d2);

		assertTrue(true);
	}

	private void print(CompareResult result, Date d1, Date d2) {
		printDates(d1, d2);
		printResult(result);
		System.out.println("\n\n");
	}

	private void printDates(Date d1, Date d2) {
		System.out.println(String.format("Compare: %s and %s", df.format(d1),
				df.format(d2)));
	}

	private void printResult(CompareResult result) {
		System.out.println(String.format("%s: %s", result.getDescription(),
				result.getValue()));
		for (CompareResult rs : result.getSubrestults()) {
			printResult(rs);
		}
	}

}
