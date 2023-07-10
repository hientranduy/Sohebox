package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.RequestExternalTbl;
import com.hientran.sohebox.sco.RequestExternalSCO;
import com.hientran.sohebox.specification.RequestExternalSpecs;

public interface RequestExternalRepository
		extends JpaRepository<RequestExternalTbl, Long>, JpaSpecificationExecutor<RequestExternalTbl>, BaseRepository {

	RequestExternalSpecs specs = new RequestExternalSpecs();

	/**
	 *
	 * Get all data
	 *
	 * @return
	 */
	public default Page<RequestExternalTbl> findAll(RequestExternalSCO sco) {

		// Declare result
		Page<RequestExternalTbl> result = null;

		// Create data filter
		Specification<RequestExternalTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<RequestExternalTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}
}
