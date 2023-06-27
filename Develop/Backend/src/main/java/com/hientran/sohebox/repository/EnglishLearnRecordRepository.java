package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.EnglishLearnRecordTbl;
import com.hientran.sohebox.sco.EnglishLearnRecordSCO;
import com.hientran.sohebox.specification.EnglishLearnRecordSpecs;

/**
 * @author hientran
 */
public interface EnglishLearnRecordRepository extends JpaRepository<EnglishLearnRecordTbl, Long>,
		JpaSpecificationExecutor<EnglishLearnRecordTbl>, BaseRepository {

	EnglishLearnRecordSpecs specs = new EnglishLearnRecordSpecs();

	/**
	 * 
	 * Get all data
	 *
	 * @return
	 */
	public default Page<EnglishLearnRecordTbl> findAll(EnglishLearnRecordSCO sco) {

		// Declare result
		Page<EnglishLearnRecordTbl> result = null;

		// Create data filter
		Specification<EnglishLearnRecordTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<EnglishLearnRecordTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}
}
