package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.UserActivityTbl;
import com.hientran.sohebox.sco.UserActivitySCO;
import com.hientran.sohebox.specification.UserActivitySpecs;

public interface UserActivityRepository
		extends JpaRepository<UserActivityTbl, Long>, JpaSpecificationExecutor<UserActivityTbl>, BaseRepository {

	UserActivitySpecs specs = new UserActivitySpecs();

	/**
	 *
	 * Get all data
	 *
	 * @return
	 */
	public default Page<UserActivityTbl> findAll(UserActivitySCO sco) {

		// Declare result
		Page<UserActivityTbl> result = null;

		// Create data filter
		Specification<UserActivityTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<UserActivityTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}

}
