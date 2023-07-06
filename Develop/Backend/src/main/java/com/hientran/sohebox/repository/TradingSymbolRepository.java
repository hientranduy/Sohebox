package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.TradingSymbolTbl;
import com.hientran.sohebox.sco.TradingSymbolSCO;
import com.hientran.sohebox.specification.TradingSymbolSpecs;

public interface TradingSymbolRepository
		extends JpaRepository<TradingSymbolTbl, Long>, JpaSpecificationExecutor<TradingSymbolTbl>, BaseRepository {

	TradingSymbolSpecs specs = new TradingSymbolSpecs();

	/**
	 * 
	 * Get all data
	 *
	 * @return
	 */
	public default Page<TradingSymbolTbl> findAll(TradingSymbolSCO sco) {

		// Declare result
		Page<TradingSymbolTbl> result = null;

		// Create data filter
		Specification<TradingSymbolTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<TradingSymbolTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}
}
