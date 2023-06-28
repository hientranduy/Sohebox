package com.hientran.sohebox.cache;

import lombok.Data;

@Data
public class BaseCache {
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
