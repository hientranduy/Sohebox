package com.hientran.sohebox.repository;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.sco.Sorter;

public interface BaseRepository {
	public default Pageable createPageable(Integer pageToGet, Integer maxRecordPerPage, List<Sorter> sorters,
			Boolean reportFlag) {
		// Declare return
		Pageable result;

		// Prepare Sort
		Sort sort = null;
		if (!CollectionUtils.isEmpty(sorters)) {
			for (Sorter sorter : sorters) {
				if (sorter.getProperty() != null) {
					// Create sort
					Sort checkSort = null;
					if (StringUtils.equals(sorter.getDirection().toUpperCase(), DBConstants.DIRECTION_DECCENT)) {
						checkSort = Sort.by(Sort.Direction.DESC, sorter.getProperty());
					} else {
						checkSort = Sort.by(Sort.Direction.ASC, sorter.getProperty());
					}

					// Add sort
					if (sort == null) {
						sort = checkSort;
					} else {
						sort = sort.and(checkSort);
					}
				}
			}
		}

		// Get default configuration
		int dataReturnMaxRecordPerPage = Integer.valueOf(1000);
		int dataReturnFirstPage = Integer.valueOf(0);

		// Calculate max record per page
		if (maxRecordPerPage == null || maxRecordPerPage > dataReturnMaxRecordPerPage) {
			if (reportFlag == null || !reportFlag) {
				maxRecordPerPage = dataReturnMaxRecordPerPage;
			}
		}

		// Calculate pageToGet; index from 0
		if (pageToGet == null || pageToGet <= 0) {
			pageToGet = dataReturnFirstPage;
		}

		// Prepare page able
		Pageable pageable = null;
		if (sort != null) {
			pageable = PageRequest.of(pageToGet, maxRecordPerPage, sort);
		} else {
			pageable = PageRequest.of(pageToGet, maxRecordPerPage);
		}

		// Return
		result = pageable;
		return result;
	}
}
