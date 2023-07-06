package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.YoutubeChannelTbl;
import com.hientran.sohebox.sco.YoutubeChannelSCO;
import com.hientran.sohebox.specification.YoutubeChannelSpecs;

public interface YoutubeChannelRepository
		extends JpaRepository<YoutubeChannelTbl, Long>, JpaSpecificationExecutor<YoutubeChannelTbl>, BaseRepository {

	YoutubeChannelSpecs specs = new YoutubeChannelSpecs();

	/**
	 * 
	 * Get all data
	 *
	 * @return
	 */
	public default Page<YoutubeChannelTbl> findAll(YoutubeChannelSCO sco) {

		// Declare result
		Page<YoutubeChannelTbl> result = null;

		// Create data filter
		Specification<YoutubeChannelTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<YoutubeChannelTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}
}
