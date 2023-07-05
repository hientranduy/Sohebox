package com.hientran.sohebox.transformer;

import org.springframework.data.domain.Page;

import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
public class BaseTransformer {

	/**
	 * 
	 * Set page reponse header
	 *
	 * @param listData
	 * @param result
	 */
	protected void setPageHeader(Page<?> listData, PageResultVO<?> result) {
		result.setTotalPage(listData.getTotalPages());
		result.setTotalElement(listData.getTotalElements());
		result.setCurrentPage(listData.getPageable().getPageNumber());
		result.setPageSize(listData.getPageable().getPageSize());
	}
}
