package de.uni.mannheim.semantic.comparison;

import de.uni.mannheim.semantic.model.CompareResult;

public abstract class AbstractComparator<T extends Object> {

	public abstract CompareResult compare(T o1, T o2);
	
}
