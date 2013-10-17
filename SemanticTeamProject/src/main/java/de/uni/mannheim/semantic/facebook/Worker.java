package de.uni.mannheim.semantic.facebook;

import facebook4j.Achievement;
import facebook4j.Activity;
import facebook4j.Checkin;
import facebook4j.Facebook;
import facebook4j.FacebookException;

public class Worker {

	public Worker(Facebook fb) {
		// TODO Auto-generated constructor stub
		
		try {
			for (Achievement a : fb.getAchievements()) {
				System.out.println(a.getAchievement().getTitle());
			}
			
			
			for (Activity a : fb.getActivities()) {
				System.out.println(a.getName());
			}
			
			for (Checkin a : fb.getCheckins()) {
				System.out.println(a.getMessage());
			}
			
			
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
