package de.uni.mannheim.semantic.model;

import java.util.Set;

public class Interest {

	private String kind;
	private String cover_url;
	private Set<String> genre;
	private String id;
	private String name;
	private String location;

	public Interest(String kind, String cover_url, Set<String> set, String id,
			String name, String location) {
		this.kind = kind;
		this.cover_url = cover_url;
		this.genre = set;
		this.id = id;
		this.name = name;
		this.location = location;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	

}
