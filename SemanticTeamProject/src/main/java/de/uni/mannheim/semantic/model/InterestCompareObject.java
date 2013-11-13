package de.uni.mannheim.semantic.model;

import java.util.ArrayList;
import java.util.List;

public class InterestCompareObject {

	private String genre;
	private List<Interest> facebookInterests;
	private List<Interest> celebrityInterests;

	public InterestCompareObject(String genre) {
		this.genre = genre;
	}
	
	public String getGenre() {
		return genre;
	}

	public List<Interest> getFacebookInterests() {
		if (facebookInterests == null) {
			facebookInterests = new ArrayList<Interest>();
		}
		return facebookInterests;
	}

	public List<Interest> getCelebrityInterests() {
		if (celebrityInterests == null) {
			celebrityInterests = new ArrayList<Interest>();
		}
		return celebrityInterests;
	}

	public String toString() {
		return genre;
	}
	
}
