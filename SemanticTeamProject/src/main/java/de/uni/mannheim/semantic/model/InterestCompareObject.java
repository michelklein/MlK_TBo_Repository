package de.uni.mannheim.semantic.model;

import java.util.ArrayList;
import java.util.List;

public class InterestCompareObject {

	private String genre;
	private List<String> picURLs1;
	private List<String> picURLs2;

	public InterestCompareObject(String genre) {
		this.genre = genre;
	}
	
	public String getGenre() {
		return genre;
	}

	public List<String> getPicURLs1() {
		if (picURLs1 == null) {
			picURLs1 = new ArrayList<String>();
		}
		return picURLs1;
	}

	public List<String> getPicURLs2() {
		if (picURLs2 == null) {
			picURLs2 = new ArrayList<String>();
		}
		return picURLs2;
	}

	public String toString() {
		return genre;
	}
	
}
