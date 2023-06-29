package com.hientran.sohebox.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.enums.FoodTypeTblEnum;
import com.hientran.sohebox.entity.FoodTypeTbl;
import com.hientran.sohebox.sco.FoodTypeSCO;
import com.hientran.sohebox.sco.Sorter;
import com.hientran.sohebox.specification.FoodTypeSpecs;

public interface FoodTypeRepository
		extends JpaRepository<FoodTypeTbl, Long>, JpaSpecificationExecutor<FoodTypeTbl>, BaseRepository {

	FoodTypeTbl findFirstByTypeClassAndTypeCode(String typeClass, String typeCode);

	FoodTypeSpecs specs = new FoodTypeSpecs();

	public default Page<FoodTypeTbl> findAll(FoodTypeSCO sco) {

		// Declare result
		Page<FoodTypeTbl> result = null;

		// Create data filter
		Specification<FoodTypeTbl> specific = specs.buildSpecification(sco);

		// Set default sort if not have
		if (sco.getSorters() == null) {
			// Sort default by type code
			Sorter sort = new Sorter();
			sort.setDirection(DBConstants.DIRECTION_ACCENT);
			sort.setProperty(FoodTypeTblEnum.typeCode.name());

			List<Sorter> sorters = new ArrayList<>();
			sorters.add(sort);

			sco.setSorters(sorters);
		}

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<FoodTypeTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}
}
