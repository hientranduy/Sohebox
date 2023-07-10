package com.hientran.sohebox.utils;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hientran.sohebox.constants.DBConstants;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VelocityUtils {

	private final VelocityEngine velocityEngine;

	/**
	 *
	 * Create text by VM template and parameters
	 *
	 * @param templatePath
	 * @param parameters
	 * @return
	 * @throws JsonProcessingException
	 */
	public String getTextByVmTemplate(String templatePath, Map<String, String> parameters)
			throws JsonProcessingException {
		// Declare result
		String result = null;

		// Prepare velocity context
		VelocityContext map = new VelocityContext();
		if (parameters != null) {
			for (Map.Entry<String, String> param : parameters.entrySet()) {
				map.put(param.getKey(), param.getValue());
			}
		}

		// Merge template with contexts
		StringWriter stringWriter = new StringWriter();
		velocityEngine.mergeTemplate(templatePath, DBConstants.ENCODING_UTF8, map, stringWriter);
		result = stringWriter.toString();

		// Return
		return result;
	}
}
