package de.uni.mannheim.semantic.comparison;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import de.uni.mannheim.semantic.model.CompareResult;

public abstract class AbstractComparator<T extends Object> {

	private Logger logger = LogManager.getLogger(AbstractComparator.class
			.getName());
	
	public abstract CompareResult compare(T o1, T o2);

	protected T o1;
	protected T o2;
	protected CompareResult result;

	protected void compareHelper(int val1, int val2, int percent,
			String description, CompareResult result) {
		int tempResult = val1 == val2 ? percent : 0;
		result.setValue(result.getValue() + tempResult);
		result.getSubresults()
				.add(new CompareResult(tempResult, description, description,
						o1, o2));
	}

	protected void compareHelper(String val1, String val2, int percent,
			String description, CompareResult result) {

		if (val1 == null || val2 == null) {
			result.getSubresults().add(
					new CompareResult(0, description, description, o1, o2));
		} else {

			int tempResult = val1.equalsIgnoreCase(val2) ? percent : 0;
			result.setValue(result.getValue() + tempResult);
			result.getSubresults().add(
					new CompareResult(tempResult, description, description, o1,
							o2));
		}
	}

	public void print() {
		printObjects(o1, o2);
		printResult(result);
		logger.debug("\n\n");
	}

	public void printObjects(T o1, T o2) {
		logger.debug(String.format("Compare: %s and %s", o1, o2));
	}

	public void printResult(CompareResult result) {
		logger.debug(String.format("%s: %s", result.getDesc1(),
				result.getValue()));
		for (CompareResult rs : result.getSubresults()) {
			printResult(rs);
		}
	}

}
