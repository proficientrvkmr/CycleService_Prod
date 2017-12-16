package com.app.util;

import java.io.IOException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Ravi Kumar
 *
 */
public class JSONConverterUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static Object fromJson(Object object, Class<?> clazz) throws JsonParseException, JsonMappingException, IOException{
		Object javaObject = objectMapper.readValue(object.toString(), clazz);
		return javaObject;
	}
	
	public static JSONObject toJson(Object object) throws JsonProcessingException, JSONException{
		String jsonInString = objectMapper.writeValueAsString(object);
		return new JSONObject(jsonInString);
	}
	
	public static JSONArray toJsonArray(Object object) throws JsonProcessingException, JSONException{
		String jsonInString = objectMapper.writeValueAsString(object);
		return new JSONArray(jsonInString);
	}
	
	public static String toJsonString(Object object) throws JsonProcessingException{
		String jsonInString = objectMapper.writeValueAsString(object);
		return jsonInString;
	}
}
