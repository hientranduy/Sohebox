package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.CountryTblEnum;
import com.hientran.sohebox.entity.CountryTbl;
import com.hientran.sohebox.sco.CountrySCO;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CountrySpecs extends GenericSpecs {

	public Specification<CountryTbl> buildSpecification(CountrySCO sco) {
		// Declare result
		Specification<CountryTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			specification = specification.and(buildSearchText(CountryTblEnum.name.name(), sco.getName()));
			specification = specification.and(buildSearchText(CountryTblEnum.note.name(), sco.getNote()));
		}

		// Return result
		return specification;
	}
}