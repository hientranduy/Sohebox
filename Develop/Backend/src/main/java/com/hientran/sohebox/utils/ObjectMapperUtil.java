package com.hientran.sohebox.utils;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Object Mapper Utility
 *
 * @author hientran
 */
@Component
public class ObjectMapperUtil implements Serializable {

	private static final long serialVersionUID = 7691347384413092645L;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 
	 * Create JSON String from Object
	 *
	 * @param value
	 * @return
	 * @throws JsonProcessingException
	 */
	public String writeValueAsString(Object value) throws JsonProcessingException {
		String jsonString = null;
		if (null != value) {
			jsonString = objectMapper.writeValueAsString(value);
		}
		return jsonString;
	}

	/**
	 * 
	 * Create defined object from JSON String and Class
	 *
	 * @param content
	 * @param valueType
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public <T> T readValue(String content, Class<T> valueType)
			throws JsonParseException, JsonMappingException, IOException {
		T result = null;
		if (StringUtils.isNotBlank(content)) {
			result = objectMapper.readValue(content, valueType);
		}
		return result;
	}

	/**
	 * 
	 * Create defined object from object and Class
	 *
	 * @param object
	 * @param valueType
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public <T> T readValue(Object object, Class<T> valueType)
			throws JsonParseException, JsonMappingException, IOException {
		T result = null;
		if (object != null) {
			result = objectMapper.readValue(writeValueAsString(object), valueType);
		}
		return result;
	}

	/**
	 * 
	 * Create defined object from JSON String and TypeReference
	 *
	 * @param content
	 * @param valueTypeRef
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public <T> T readValue(String content, TypeReference<?> valueTypeRef)
			throws JsonParseException, JsonMappingException, IOException {
		T result = null;
		if (StringUtils.isNotBlank(content)) {
			result = (T) objectMapper.readValue(content, valueTypeRef);
		}
		return result;
	}

	/**
	 * 
	 * Create defined object from object and TypeReference
	 *
	 * @param content
	 * @param valueTypeRef
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public <T> T readValue(Object object, TypeReference<?> valueTypeRef)
			throws JsonParseException, JsonMappingException, IOException {
		T result = null;
		if (object != null) {
			result = (T) objectMapper.readValue(writeValueAsString(object), valueTypeRef);
		}
		return result;
	}
}
