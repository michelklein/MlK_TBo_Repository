package de.uni.mannheim.semantic.model;

import de.uni.mannheim.semantic.facebook.FBParser;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Page;
import facebook4j.internal.org.json.JSONObject;

public class Institution {
	private String name;
	private Location loc;
	public Institution(String id) {
		Page p;
		try {
			p = FBParser.getFB().getPage(id);
			name=p.getName();
			facebook4j.Place.Location location = p.getLocation();
			if(location!=null){
				loc= new Location(location.getLongitude(), location.getLatitude());
			}
//			FBParser.TBoSuperDuperPrinter(p);
			
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public Institution(String n, String lo, String la) {
		name=n;
		if(lo!=null&&la!=null){
		loc= new Location(Double.parseDouble(lo), Double.parseDouble(la));
		}
		}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	
}
