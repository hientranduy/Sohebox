package com.hientran.sohebox.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageUtil {

	@Autowired
	private static ResourceBundleMessageSource messageSource;

	public static String buildMessage(String messageCode, Object[] params) {
		return messageSource.getMessage(messageCode, params, null);

	}
}
