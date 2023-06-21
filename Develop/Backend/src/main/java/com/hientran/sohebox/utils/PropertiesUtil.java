package com.hientran.sohebox.utils;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class PropertiesUtil {

	public static String getProperty(String inputKey) {
		InputStream inputStream = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("application-runtime.yml");
		Map<String, Object> obj = new Yaml().load(inputStream);
		String[] partKeys = inputKey.split("\\.");
		String result = null;
		for (int i = 0; i < partKeys.length; i++) {
			if (i < partKeys.length - 1) {
				obj = (Map<String, Object>) obj.get(partKeys[i]);
			} else {
				Object abc = obj.get(partKeys[i]);
				result = abc.toString();
			}
		}
		return result;
	}
}
