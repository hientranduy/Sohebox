package com.hientran.sohebox.cache;

import org.springframework.data.domain.Page;

import com.hientran.sohebox.vo.PageResultVO;

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
	
	protected void setPageHeader(Page<?> listData, PageResultVO<?> result) {
		result.setTotalPage(listData.getTotalPages());
		result.setTotalElement(listData.getTotalElements());
		result.setCurrentPage(listData.getPageable().getPageNumber());
		result.setPageSize(listData.getPageable().getPageSize());
	}
}
