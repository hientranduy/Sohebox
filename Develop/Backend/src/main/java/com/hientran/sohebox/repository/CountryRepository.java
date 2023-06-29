package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.CountryTbl;
import com.hientran.sohebox.sco.CountrySCO;
import com.hientran.sohebox.specification.CountrySpecs;

/**
 * @author hientran
 */
public interface CountryRepository
		extends JpaRepository<CountryTbl, Long>, JpaSpecificationExecutor<CountryTbl>, BaseRepository {

	CountrySpecs specs = new CountrySpecs();

	/**
	 * 
	 * Get all data
	 *
	 * @return
	 */
	public default Page<CountryTbl> findAll(CountrySCO sco) {

		// Declare result
		Page<CountryTbl> result = null;

		// Create data filter
		Specification<CountryTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<CountryTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}
}
