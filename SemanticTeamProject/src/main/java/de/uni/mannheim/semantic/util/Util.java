package de.uni.mannheim.semantic.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Util {

	private static final Logger logger = LogManager.getLogger(Util.class
			.getName());
	
	public static void print(Object p) {
		try {
			for (Field field : getInheritedFields(p.getClass())) {
				field.setAccessible(true);
				String fname = field.getName();
				Object value;
				value = field.get(p);
				logger.info("Field: " + fname + " : " + value);
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.toString(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.toString(), e);
		}
	}

	private static List<Field> getInheritedFields(Class<?> type) {
		List<Field> fields = new ArrayList<Field>();
		for (Class<?> c = type; c != null; c = c.getSuperclass()) {
			fields.addAll(Arrays.asList(c.getDeclaredFields()));
		}
		return fields;
	}
	
}
