package de.uni.mannheim.semantic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import de.uni.mannheim.semantic.FetchDataServlet;

/**
 * Util class for the simple access to properties file 
 * 
 * @author Michel
 */
public class PropertiesUtils {

	private static final Logger logger = LogManager.getLogger(PropertiesUtils.class
			.getName());
	
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
			try {
				props.load(in);
			} catch (IOException e) {
				logger.error(e.toString(), e);
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
