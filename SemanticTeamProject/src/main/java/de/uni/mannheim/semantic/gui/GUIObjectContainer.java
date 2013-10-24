package de.uni.mannheim.semantic.gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import de.uni.mannheim.semantic.model.FBPerson;

public class GUIObjectContainer {

	private FBPerson fbp;
	private List<String> celebrities;

	public GUIObjectContainer(FBPerson fbp, List<String> celebrities) {
		this.fbp = fbp;
		this.celebrities = celebrities;
	}

	public FBPerson getFbp() {
		return fbp;
	}
	
	public List<String> getCelebrities() {
		if(celebrities == null) {
			celebrities = new ArrayList<String>();
		}
		return celebrities;
	}
	
	public String getCelebritiesAsJson() {
		JsonObject json = new JsonObject();
		JsonArray array = new JsonArray();
		for(String celebrity : celebrities) {
			json.put("celebritie", celebrity);
			array.add(celebrity);
		}
		return array.toString();
	}
}
