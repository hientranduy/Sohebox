package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.EnglishTypeTblEnum;
import com.hientran.sohebox.entity.EnglishTypeTbl;
import com.hientran.sohebox.sco.EnglishTypeSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class EnglishTypeSpecs extends GenericSpecs {

	public Specification<EnglishTypeTbl> buildSpecification(EnglishTypeSCO sco) {
		// Declare result
		Specification<EnglishTypeTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification
						.or(buildSearchText(EnglishTypeTblEnum.typeClass.name(), sco.getTypeClass()));
				specification = specification
						.or(buildSearchText(EnglishTypeTblEnum.typeCode.name(), sco.getTypeCode()));
				specification = specification
						.or(buildSearchText(EnglishTypeTblEnum.typeName.name(), sco.getTypeName()));
				specification = specification
						.or(buildSearchText(EnglishTypeTblEnum.description.name(), sco.getDescription()));
				specification = specification
						.or(buildSearchBoolean(EnglishTypeTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
			} else {
				specification = specification
						.and(buildSearchText(EnglishTypeTblEnum.typeClass.name(), sco.getTypeClass()));
				specification = specification
						.and(buildSearchText(EnglishTypeTblEnum.typeCode.name(), sco.getTypeCode()));
				specification = specification
						.and(buildSearchText(EnglishTypeTblEnum.typeName.name(), sco.getTypeName()));
				specification = specification
						.and(buildSearchText(EnglishTypeTblEnum.description.name(), sco.getDescription()));
				specification = specification
						.and(buildSearchBoolean(EnglishTypeTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
			}
		}

		// Return result
		return specification;
	}
}