package com.hientran.sohebox.cache;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Data;

@Data
public class BaseCache {
	@PersistenceContext
	protected EntityManager entityManager;

	protected String formatTypeClass(String typeClass) {
		return typeClass.toUpperCase();
	}

	protected String formatTypeCode(String typeCode) {
		return typeCode.replaceAll(" ", "_").toUpperCase();
	}

	protected String formatTypeMapKey(String typeClass, String typeCode) {
		return formatTypeClass(typeClass) + "-" + formatTypeCode(typeCode);
	}
}
