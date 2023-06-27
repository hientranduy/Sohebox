package com.hientran.sohebox.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.enums.ConfigTblEnum;
import com.hientran.sohebox.entity.ConfigTbl;
import com.hientran.sohebox.sco.ConfigSCO;
import com.hientran.sohebox.sco.Sorter;
import com.hientran.sohebox.specification.ConfigSpecs;

/**
 * @author hientran
 */
public interface ConfigRepository
		extends JpaRepository<ConfigTbl, Long>, JpaSpecificationExecutor<ConfigTbl>, BaseRepository {
	ConfigSpecs specs = new ConfigSpecs();

	/**
	 * 
	 * Get all data
	 *
	 * @return
	 */
	public default Page<ConfigTbl> findAll(ConfigSCO sco) {

		// Declare result
		Page<ConfigTbl> result = null;

		// Create data filter
		Specification<ConfigTbl> specific = specs.buildSpecification(sco);

		// Set default sort if not have
		if (sco.getSorters() == null) {
			// Sort default by config code
			Sorter sort = new Sorter();
			sort.setDirection(DBConstants.DIRECTION_ACCENT);
			sort.setProperty(ConfigTblEnum.configKey.name());

			List<Sorter> sorters = new ArrayList<>();
			sorters.add(sort);

			sco.setSorters(sorters);
		}

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<ConfigTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}

}
