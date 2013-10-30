package de.uni.mannheim.semantic.model;

public class Interest {
private String kind;
private String cover_url;
private String genre;
private String id;
private String name;
public Interest(String kind, String cover_url, String genre, String id, String name) {
	this.kind = kind;
	this.cover_url = cover_url;
	this.genre = genre;
	this.id = id;
	this.name = name;
}
public String getKind() {
	return kind;
}
public void setKind(String kind) {
	this.kind = kind;
}
public String getGenre() {
	return genre;
}
public void setGenre(String genre) {
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




}
