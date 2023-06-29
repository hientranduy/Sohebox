package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.YoutubeVideoTbl;
import com.hientran.sohebox.sco.YoutubeVideoSCO;
import com.hientran.sohebox.specification.YoutubeVideoSpecs;

/**
 * @author hientran
 */
public interface YoutubeVideoRepository
		extends JpaRepository<YoutubeVideoTbl, Long>, JpaSpecificationExecutor<YoutubeVideoTbl>, BaseRepository {

	YoutubeVideoSpecs specs = new YoutubeVideoSpecs();

	/**
	 * 
	 * Get all data
	 *
	 * @return
	 */
	public default Page<YoutubeVideoTbl> findAll(YoutubeVideoSCO sco) {

		// Declare result
		Page<YoutubeVideoTbl> result = null;

		// Create data filter
		Specification<YoutubeVideoTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<YoutubeVideoTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}
}
