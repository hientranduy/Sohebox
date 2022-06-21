package com.hientran.sohebox.utils;

import java.io.Serializable;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Utility class for general logging
 *
 * @author hientran
 */
public class LogUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(LogUtils.class);

    /**
     * 
     * Get date with time is 0:0:0
     *
     * @param aDate
     * @return
     * @throws Exception
     */
    public static void writeLogError(Exception e) {
        logger.error(ExceptionUtils.getFullStackTrace(e));
    }
}
