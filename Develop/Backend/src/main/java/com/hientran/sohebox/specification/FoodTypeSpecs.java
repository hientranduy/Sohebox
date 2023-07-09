package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.FoodTypeTbl;
import com.hientran.sohebox.sco.FoodTypeSCO;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FoodTypeSpecs extends GenericSpecs {
	public enum FoodTypeTblEnum {
		typeClass, typeCode, typeName, description, deleteFlag
	}

	public Specification<FoodTypeTbl> buildSpecification(FoodTypeSCO sco) {
		// Declare result
		Specification<FoodTypeTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification.or(buildSearchText(FoodTypeTblEnum.typeClass.name(), sco.getTypeClass()));
				specification = specification.or(buildSearchText(FoodTypeTblEnum.typeCode.name(), sco.getTypeCode()));
				specification = specification.or(buildSearchText(FoodTypeTblEnum.typeName.name(), sco.getTypeName()));
				specification = specification
						.or(buildSearchText(FoodTypeTblEnum.description.name(), sco.getDescription()));
				specification = specification
						.and(buildSearchBoolean(FoodTypeTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
			} else {
				specification = specification
						.and(buildSearchText(FoodTypeTblEnum.typeClass.name(), sco.getTypeClass()));
				specification = specification.and(buildSearchText(FoodTypeTblEnum.typeCode.name(), sco.getTypeCode()));
				specification = specification.and(buildSearchText(FoodTypeTblEnum.typeName.name(), sco.getTypeName()));
				specification = specification
						.and(buildSearchText(FoodTypeTblEnum.description.name(), sco.getDescription()));
				specification = specification
						.and(buildSearchBoolean(FoodTypeTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
			}
		}

		// Return result
		return specification;
	}
}