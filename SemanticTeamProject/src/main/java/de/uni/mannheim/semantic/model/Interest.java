package de.uni.mannheim.semantic.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import de.uni.mannheim.semantic.jena.CelebritiesFetcher;

public class Interest {

	private static SimpleDateFormat sdfToDate = new SimpleDateFormat(
			"yyyyMMdd");
	private Logger logger = LogManager.getLogger(SimpleDateFormat.class
			.getName());
	
	private String kind;
	private String cover_url;
	private Set<String> genre;
	private String id;
	private String name;
	private String location;
	private String releaseDate;

	public Interest(String kind, String cover_url, Set<String> set, String id,
			String name, String location, String releaseDate) {
		this.kind = kind;
		this.cover_url = cover_url;
		this.genre = set;
		this.id = id;
		this.name = name;
		this.location = location;
		this.releaseDate = releaseDate;
	}
	

	public Date getReleaseDate() {
		if(releaseDate == null) {
			return null;
		}
		try {
			return sdfToDate.parse(releaseDate);
		} catch (ParseException e) {
			logger.error(e.toString(),e);
		}
		return null;
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
	
	public String toString() {
		return String.format("%s, %s, %s",  name, genre, location);
	}
	

}
