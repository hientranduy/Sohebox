package com.hientran.sohebox.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.entity.CryptoPortfolioTbl;
import com.hientran.sohebox.sco.CryptoPortfolioSCO;
import com.hientran.sohebox.sco.Sorter;
import com.hientran.sohebox.specification.CryptoPortfolioSpecs;
import com.hientran.sohebox.specification.CryptoPortfolioSpecs.CryptoPortfolioTblEnum;

public interface CryptoPortfolioRepository
		extends JpaRepository<CryptoPortfolioTbl, Long>, JpaSpecificationExecutor<CryptoPortfolioTbl>, BaseRepository {
	CryptoPortfolioSpecs specs = new CryptoPortfolioSpecs();

	/**
	 * Get all data
	 */
	public default Page<CryptoPortfolioTbl> findAll(CryptoPortfolioSCO sco) {

		// Declare result
		Page<CryptoPortfolioTbl> result = null;

		// Create data filter
		Specification<CryptoPortfolioTbl> specific = specs.buildSpecification(sco);

		// Set default sort if not have
		if (sco.getSorters() == null) {
			Sorter sort1 = new Sorter();
			sort1.setDirection(DBConstants.DIRECTION_ACCENT);
			sort1.setProperty(CryptoPortfolioTblEnum.token.name());

			Sorter sort2 = new Sorter();
			sort2.setDirection(DBConstants.DIRECTION_ACCENT);
			sort2.setProperty(CryptoPortfolioTblEnum.starname.name());

			List<Sorter> sorters = new ArrayList<>();
			sorters.add(sort1);
			sorters.add(sort2);

			sco.setSorters(sorters);
		}

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<CryptoPortfolioTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}

}
