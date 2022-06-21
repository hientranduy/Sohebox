package com.hientran.sohebox.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * Return http not found
 *
 * @author hientran
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceNotFoundException.class);

    private String tableName;

    private String fieldName;

    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.tableName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        LOGGER.error(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }

    public String getResourceName() {
        return tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
