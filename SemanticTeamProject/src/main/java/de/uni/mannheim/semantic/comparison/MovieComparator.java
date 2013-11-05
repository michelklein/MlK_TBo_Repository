package de.uni.mannheim.semantic.comparison;

import java.util.ArrayList;
import java.util.List;

import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Interest;

public class MovieComparator extends AbstractComparator<String> {

	@Override
	public CompareResult compare(String o1, String o2) {
		// TODO Auto-generated method stub

		result = new CompareResult();
		result.setDescription("TBo Desc");
		result.getSubresults().add(new CompareResult(10, "Zehn"));
		result.getSubresults().add(new CompareResult(20, "Zwanzig"));
		result.setValue(30);

		return result;
	}

	public List<CompareResult> compareList(List<Interest> fbList,
			List<Interest> cList) {
		// TODO Auto-generated method stub

		List<CompareResult> resultList = new ArrayList<CompareResult>();

		// for (Interest i : fbList) {
		// i.getGenre()
		// }
		CompareResult overall = new CompareResult(30, "TBo Desc");
		resultList.add(overall);
		overall.getSubresults().add(new CompareResult(10, "Zehn"));
		overall.getSubresults().add(new CompareResult(20, "Zwanzig"));

		return resultList;
	}
}
