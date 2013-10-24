package de.uni.mannheim.semantic.gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import de.uni.mannheim.semantic.model.CelPerson;
import de.uni.mannheim.semantic.model.FBPerson;

public class GUIObjectContainer {

	private FBPerson fbp;
	private CelPerson cp;
	private List<String> celebrities;

	public GUIObjectContainer(FBPerson fbp,CelPerson cp, List<String> celebrities) {
		this.fbp = fbp;
		this.cp = cp;
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

	public CelPerson getCp() {
		return cp;
	}

	public void setCp(CelPerson cp) {
		this.cp = cp;
	}
}
