package de.uni.mannheim.semantic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Util class for the simple access to properties file 
 * 
 * @author Michel
 */
public class PropertiesUtils {

	/**
	 * private constructor because of the static methods.
	 */
	private PropertiesUtils() {
	}

	/**
	 * Load a properties file from the classpath
	 * 
	 * @param propsName
	 * @return Properties
	 * @throws IOException 
	 * @throws Exception
	 */
	public static Properties load(String propsName)  {
	     InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(propsName);
		Properties props = new Properties();
//		URL url = ClassLoader.getSystemResource(propsName);
			try {
				props.load(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return props;
	}

	/**
	 * Load a Properties File
	 * 
	 * @param propsFile
	 * @return Properties
	 * @throws IOException
	 */
	public static Properties load(File propsFile) throws IOException {
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(propsFile);
		props.load(fis);
		fis.close();
		return props;
	}
}
