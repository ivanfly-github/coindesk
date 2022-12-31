package com.cathay.coindesk.util;

import com.cathay.coindesk.ex.BpiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static <T> T toObject(String json, Class<T> valueType) {
		try {
			return mapper.readValue(json, valueType);
		} catch (JsonProcessingException e) {
			throw new BpiException(e);
		}
	}
	
	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new BpiException(e);
		}
	}

}
