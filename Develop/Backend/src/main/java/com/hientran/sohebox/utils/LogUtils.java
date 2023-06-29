package com.hientran.sohebox.utils;

import org.apache.commons.lang.exception.ExceptionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtils {
	public static void writeLogError(Exception e) {
		log.error(ExceptionUtils.getFullStackTrace(e));
	}
}
