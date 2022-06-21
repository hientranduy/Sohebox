package com.hientran.sohebox.transformer;

import java.io.Serializable;

import org.springframework.data.domain.Page;

import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
public class BaseTransformer implements Serializable {

    private static final long serialVersionUID = 1L;

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
