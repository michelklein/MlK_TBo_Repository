package de.uni.mannheim.semantic.model;

import facebook4j.internal.org.json.JSONObject;

public abstract class AbstractToJson {

	public String toJsonString() {
		JSONObject json = new JSONObject(this);
		return json.toString();
	}

	
}
