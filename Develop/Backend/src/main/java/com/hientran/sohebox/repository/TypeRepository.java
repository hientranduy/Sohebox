package com.hientran.sohebox.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.sco.Sorter;
import com.hientran.sohebox.sco.TypeSCO;
import com.hientran.sohebox.specification.TypeSpecs;
import com.hientran.sohebox.specification.TypeSpecs.TypeTblEnum;

public interface TypeRepository
		extends JpaRepository<TypeTbl, Long>, JpaSpecificationExecutor<TypeTbl>, BaseRepository {

	TypeTbl findFirstByTypeClassAndTypeCode(String typeClass, String typeCode);

	TypeSpecs specs = new TypeSpecs();

	/**
	 * 
	 * Get all data
	 *
	 * @return
	 */
	public default Page<TypeTbl> findAll(TypeSCO sco) {

		// Declare result
		Page<TypeTbl> result = null;

		// Create data filter
		Specification<TypeTbl> specific = specs.buildSpecification(sco);

		// Set default sort if not have
		if (sco.getSorters() == null) {
			// Sort default by type code
			Sorter sort = new Sorter();
			sort.setDirection(DBConstants.DIRECTION_ACCENT);
			sort.setProperty(TypeTblEnum.typeCode.name());

			List<Sorter> sorters = new ArrayList<>();
			sorters.add(sort);

			sco.setSorters(sorters);
		}

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<TypeTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}
}
