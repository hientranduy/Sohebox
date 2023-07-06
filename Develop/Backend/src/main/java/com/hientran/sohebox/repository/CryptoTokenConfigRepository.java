package com.hientran.sohebox.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.enums.CryptoTokenConfigTblEnum;
import com.hientran.sohebox.entity.CryptoTokenConfigTbl;
import com.hientran.sohebox.sco.CryptoTokenConfigSCO;
import com.hientran.sohebox.sco.Sorter;
import com.hientran.sohebox.specification.CryptoTokenConfigSpecs;

public interface CryptoTokenConfigRepository extends JpaRepository<CryptoTokenConfigTbl, Long>,
		JpaSpecificationExecutor<CryptoTokenConfigTbl>, BaseRepository {
	CryptoTokenConfigSpecs specs = new CryptoTokenConfigSpecs();

	/**
	 * Get all data
	 */
	public default Page<CryptoTokenConfigTbl> findAll(CryptoTokenConfigSCO sco) {

		// Declare result
		Page<CryptoTokenConfigTbl> result = null;

		// Create data filter
		Specification<CryptoTokenConfigTbl> specific = specs.buildSpecification(sco);

		// Set default sort if not have
		if (sco.getSorters() == null) {
			Sorter sort = new Sorter();
			sort.setDirection(DBConstants.DIRECTION_ACCENT);
			sort.setProperty(CryptoTokenConfigTblEnum.tokenCode.name());

			List<Sorter> sorters = new ArrayList<>();
			sorters.add(sort);

			sco.setSorters(sorters);
		}

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<CryptoTokenConfigTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}

}
