package com.hientran.sohebox.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class BaseCache {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	protected ResourceBundleMessageSource messageSource;

	protected String buildMessage(String messageCode, Object[] params) {
		return messageSource.getMessage(messageCode, params, null);

	}
}
