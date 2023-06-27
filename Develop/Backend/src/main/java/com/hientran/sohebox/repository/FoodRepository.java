package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.FoodTbl;
import com.hientran.sohebox.sco.FoodSCO;
import com.hientran.sohebox.specification.FoodSpecs;

/**
 * @author hientran
 */
public interface FoodRepository
		extends JpaRepository<FoodTbl, Long>, JpaSpecificationExecutor<FoodTbl>, BaseRepository {

	FoodSpecs specs = new FoodSpecs();

	/**
	 * 
	 * Get all data
	 *
	 * @return
	 */
	public default Page<FoodTbl> findAll(FoodSCO sco) {

		// Declare result
		Page<FoodTbl> result = null;

		// Create data filter
		Specification<FoodTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<FoodTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}
}
