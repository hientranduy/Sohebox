package com.hientran.sohebox.transformer;

import org.springframework.data.domain.Page;

import com.hientran.sohebox.vo.PageResultVO;

public class BaseTransformer {
	/*
	 * Set page infos
	 */

	protected void setPageHeader(Page<?> listData, PageResultVO<?> result) {
		result.setTotalPage(listData.getTotalPages());
		result.setTotalElement(listData.getTotalElements());
		result.setCurrentPage(listData.getPageable().getPageNumber());
		result.setPageSize(listData.getPageable().getPageSize());
	}

	/*
	 * Format type class
	 */
	protected String formatTypeClass(String typeClass) {
		return typeClass.toUpperCase();
	}

	/*
	 * Format type code
	 */
	protected String formatTypeCode(String typeCode) {
		return typeCode.replaceAll(" ", "_").toUpperCase();
	}

	/*
	 * Format cache key - type
	 */
	protected String formatTypeMapKey(String typeClass, String typeCode) {
		return formatTypeClass(typeClass) + "-" + formatTypeCode(typeCode);
	}
}
