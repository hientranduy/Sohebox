package com.hientran.sohebox.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ObjectMapperUtil {

	private final ObjectMapper objectMapper;

	public <T> T readValue(Object object, Class<T> valueType)
			throws JsonParseException, JsonMappingException, IOException {
		T result = null;
		if (object != null) {
			result = objectMapper.readValue(writeValueAsString(object), valueType);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> T readValue(Object object, TypeReference<?> valueTypeRef)
			throws JsonParseException, JsonMappingException, IOException {
		T result = null;
		if (object != null) {
			result = (T) objectMapper.readValue(writeValueAsString(object), valueTypeRef);
		}
		return result;
	}

	public <T> T readValue(String content, Class<T> valueType)
			throws JsonParseException, JsonMappingException, IOException {
		T result = null;
		if (StringUtils.isNotBlank(content)) {
			result = objectMapper.readValue(content, valueType);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> T readValue(String content, TypeReference<?> valueTypeRef)
			throws JsonParseException, JsonMappingException, IOException {
		T result = null;
		if (StringUtils.isNotBlank(content)) {
			result = (T) objectMapper.readValue(content, valueTypeRef);
		}
		return result;
	}

	public String writeValueAsString(Object value) throws JsonProcessingException {
		String jsonString = null;
		if (null != value) {
			jsonString = objectMapper.writeValueAsString(value);
		}
		return jsonString;
	}
}
