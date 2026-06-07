package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.sco.UserSCO;
import com.hientran.sohebox.specification.UserSpecs;

public interface UserRepository
		extends JpaRepository<UserTbl, Long>, JpaSpecificationExecutor<UserTbl>, BaseRepository {

	UserSpecs specs = new UserSpecs();

	/**
	 *
	 * Get all data
	 *
	 * @return
	 */
	public default Page<UserTbl> findAll(UserSCO sco) {

		// Declare result
		Page<UserTbl> result = null;

		// Create data filter
		Specification<UserTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<UserTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}

	UserTbl findFirstByUsername(String username);
}
