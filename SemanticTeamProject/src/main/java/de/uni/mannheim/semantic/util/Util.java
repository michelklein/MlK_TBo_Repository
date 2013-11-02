package de.uni.mannheim.semantic.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

	public static void print(Object p) {
		try {
			for (Field field : getInheritedFields(p.getClass())) {
				field.setAccessible(true);
				String fname = field.getName();
				Object value;

				value = field.get(p);

				System.out.println("Field: " + fname + " : " + value);

			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
