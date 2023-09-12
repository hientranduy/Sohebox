package com.hientran.sohebox.service;

import org.springframework.data.domain.Page;

import com.hientran.sohebox.dto.PageResultVO;

public class TransformerService {
	/*
	 * Set page infos
	 */

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

	/*
	 * Hide text
	 */
	protected String hideText(String text) {
		String result = "";

		if (text.length() > 2) {

			// Add 1 first chars
			result = text.substring(0, 1);

			// Add n middle chars
			for (int i = 0; i < text.length() - 2; i++) {
				result = result + "*";
			}

			// Add 1 last chars
			result = result + text.substring(text.length() - 1);
		} else {
			result = "**";
		}

		// Return
		return result;
	}

	protected void setPageHeader(Page<?> listData, PageResultVO<?> result) {
		result.setTotalPage(listData.getTotalPages());
		result.setTotalElement(listData.getTotalElements());
		result.setCurrentPage(listData.getPageable().getPageNumber());
		result.setPageSize(listData.getPageable().getPageSize());
	}
}
