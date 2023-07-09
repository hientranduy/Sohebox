package com.hientran.sohebox.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.entity.CryptoPortfolioHistoryTbl;
import com.hientran.sohebox.entity.CryptoTokenConfigTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.sco.CryptoPortfolioHistorySCO;
import com.hientran.sohebox.sco.Sorter;
import com.hientran.sohebox.specification.CryptoPortfolioHistorySpecs;
import com.hientran.sohebox.specification.CryptoPortfolioHistorySpecs.CryptoPortfolioHistoryTblEnum;

public interface CryptoPortfolioHistoryRepository extends JpaRepository<CryptoPortfolioHistoryTbl, Long>,
		JpaSpecificationExecutor<CryptoPortfolioHistoryTbl>, BaseRepository {
	CryptoPortfolioHistorySpecs specs = new CryptoPortfolioHistorySpecs();

	CryptoPortfolioHistoryTbl findTopByUserOrderByIdDesc(UserTbl user);

	CryptoPortfolioHistoryTbl findTopByUserAndTokenOrderByTimeStampDesc(UserTbl user, CryptoTokenConfigTbl token);

	/**
	 * Get all data
	 */
	public default Page<CryptoPortfolioHistoryTbl> findAll(CryptoPortfolioHistorySCO sco) {

		// Declare result
		Page<CryptoPortfolioHistoryTbl> result = null;

		// Create data filter
		Specification<CryptoPortfolioHistoryTbl> specific = specs.buildSpecification(sco);

		// Set default sort if not have
		if (sco.getSorters() == null) {
			List<Sorter> sorters = new ArrayList<>();
			sorters.add(new Sorter(CryptoPortfolioHistoryTblEnum.token.name(), DBConstants.DIRECTION_ACCENT));
			sco.setSorters(sorters);
		}

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<CryptoPortfolioHistoryTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}

}
