package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.YoutubeChannelVideoTbl;
import com.hientran.sohebox.sco.YoutubeChannelVideoSCO;
import com.hientran.sohebox.specification.YoutubeChannelVideoSpecs;

/**
 * @author hientran
 */
public interface YoutubeChannelVideoRepository extends JpaRepository<YoutubeChannelVideoTbl, Long>,
		JpaSpecificationExecutor<YoutubeChannelVideoTbl>, BaseRepository {

	YoutubeChannelVideoSpecs specs = new YoutubeChannelVideoSpecs();

	/**
	 * 
	 * Get all data
	 *
	 * @return
	 */
	public default Page<YoutubeChannelVideoTbl> findAll(YoutubeChannelVideoSCO sco) {

		// Declare result
		Page<YoutubeChannelVideoTbl> result = null;

		// Create data filter
		Specification<YoutubeChannelVideoTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<YoutubeChannelVideoTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}
}
