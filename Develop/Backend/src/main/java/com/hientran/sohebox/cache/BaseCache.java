package com.hientran.sohebox.cache;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author hientran
 */
public class BaseCache implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    protected ResourceBundleMessageSource messageSource;

    /**
     * 
     * Build message
     *
     * @param messageCode
     * @param params
     * @return
     */
    protected String buildMessage(String messageCode, Object[] params) {
        return messageSource.getMessage(messageCode, params, null);

    }
}
