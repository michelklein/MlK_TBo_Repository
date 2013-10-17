package de.uni.mannheim.semantic.util;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

public class PropertiesUtilsTest {

	@Test
	public void test() {
		try {
			Properties prop = PropertiesUtils.load("general.properties");
			assertNotNull(prop);
			System.out.println(prop);
			assertNotNull(prop.getProperty("debug"));
			assertNotNull(prop.getProperty("oauth.appId"));
			assertNotNull(prop.getProperty("oauth.appSecret"));
			assertNotNull(prop.getProperty("oauth.permissions"));
			assertNotNull(prop.getProperty("jsonStoreEnabled"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
