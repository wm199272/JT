package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String toJSON(Object obj){
		String json=null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return json;
	}
	
	public static <T> T toObject(String json, Class<T> target) {
		T t =null;
		try {
			t = mapper.readValue(json, target);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return t;
	}
}
