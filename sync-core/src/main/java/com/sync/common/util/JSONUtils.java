package com.sync.common.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description  json工具
 */
public class JSONUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper();
	private static Logger log = LoggerFactory.getLogger(JSONUtils.class);

    private JSONUtils() {

    }

    public static ObjectMapper getInstance() {
//        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
        return objectMapper;
    }

    /**
     * javaBean,list,array convert to json string
     */
    public static String obj2json(Object obj) {

        try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
        return null;
    }

    /**
     * javaBean,list,array convert to json string
     */
    public static String obj2jsonInoreString(Object obj)  {

        try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
        return null;
    }
    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz) {
        try {
			return objectMapper.readValue(jsonStr, clazz);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
        return null;
    }

    /**
     * json string convert to map
     */
    public static <T> Map<String, Object> json2map(String jsonStr) {
        try {
			return objectMapper.readValue(jsonStr, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
        return null;
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz){
        Map<String, Map<String, Object>> map = null;
		try {
			map = objectMapper.readValue(jsonStr,
			        new TypeReference<Map<String, T>>() {
			        });
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz)
        {
        List<Map<String, Object>> list = null;
		try {
			list = objectMapper.readValue(jsonArrayStr,
			        new TypeReference<List<T>>() {
			        });
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
        List<T> result = new ArrayList<T>();
        for (Map<String, Object> map : list) {
            result.add(map2pojo(map, clazz));
        }
        return result;
    }

    /**
     * map convert to javaBean
     */
    public static <T> T map2pojo(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }
}
