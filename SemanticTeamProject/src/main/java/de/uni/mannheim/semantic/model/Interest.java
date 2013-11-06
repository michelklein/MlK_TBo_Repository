package de.uni.mannheim.semantic.model;

import java.util.Set;

public class Interest {

	private static final String COVER_URL_DUMMY = "http://www.ernieputto.de/filmstuff4/batman_begins/batman_begins_poster_us2.jpg";
	
	private String kind;
	private String cover_url;
	private Set<String> genre;
	private String id;
	private String name;

	public Interest(String kind, String cover_url, Set<String> set, String id,
			String name) {
		this.kind = kind;
		this.cover_url = cover_url;
		if(this.cover_url == null) {
			cover_url = COVER_URL_DUMMY;
		}
		this.genre = set;
		this.id = id;
		this.name = name;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Set<String> getGenre() {
		return genre;
	}

	public void setGenre(Set<String> genre) {
		this.genre = genre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCoverURL() {
		return cover_url;
	}

}
