package net.surfm.infrastructure;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ReflectionUtils;

public class ReflectionTool {

	public static List<String> getFieldNames(Class<?> src) {
		List<String> list = new ArrayList<String>();
		Field[] keys = src.getDeclaredFields();
		for (Field f : keys) {
			list.add(f.getName());
		}
		return list;
	}

	public static List<String> getFieldNamesNegative(Class<?> src,
			Class<? extends Annotation> annotation) {
		return getFieldNames(src, annotation, false);
	}

	public static List<String> getFieldNames(Class<?> src,
			Class<? extends Annotation> annotation) {
		return getFieldNames(src, annotation, true);
	}

	public static List<String> getFieldNames(Class<?> src,
			Class<? extends Annotation> annotation, boolean positive) {
		List<String> list = new ArrayList<String>();
		Field[] keys = src.getDeclaredFields();
		for (Field f : keys) {
			if ((f.getAnnotation(annotation) != null) == positive) {
				list.add(f.getName());
			}
		}
		return list;
	}

	public static Object getValue(Object src, String fieldName)
			throws IllegalArgumentException, IllegalAccessException {
		Field f = ReflectionUtils.findField(src.getClass(), fieldName);
		f.setAccessible(true);
		return f.get(src);
	}

	public static void putFieldValue(Object src, String fieldName, Object value)
			throws IllegalArgumentException, IllegalAccessException {
		Field f = ReflectionUtils.findField(src.getClass(), fieldName);
		f.setAccessible(true);
		f.set(src, value);
	}

	public static <T extends Annotation> T getAnnotation(Class<?> src,
			Class<T> annotation, String fieldName) {
		Field f = ReflectionUtils.findField(src, fieldName);
		return f.getAnnotation(annotation);
	}
	
	public static boolean hasFieldKey(Class<?> src,String fieldName){
		return getFieldNames(src).contains(fieldName);
		
	}

}