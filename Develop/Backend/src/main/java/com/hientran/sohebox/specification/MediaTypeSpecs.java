package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.MediaTypeTblEnum;
import com.hientran.sohebox.entity.MediaTypeTbl;
import com.hientran.sohebox.sco.MediaTypeSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MediaTypeSpecs extends GenericSpecs {

	public Specification<MediaTypeTbl> buildSpecification(MediaTypeSCO sco) {
		// Declare result
		Specification<MediaTypeTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification
						.or(buildSearchText(MediaTypeTblEnum.typeClass.name(), sco.getTypeClass()));
				specification = specification.or(buildSearchText(MediaTypeTblEnum.typeCode.name(), sco.getTypeCode()));
				specification = specification.or(buildSearchText(MediaTypeTblEnum.typeName.name(), sco.getTypeName()));
				specification = specification
						.or(buildSearchText(MediaTypeTblEnum.description.name(), sco.getDescription()));
				specification = specification
						.or(buildSearchBoolean(MediaTypeTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
			} else {
				specification = specification
						.and(buildSearchText(MediaTypeTblEnum.typeClass.name(), sco.getTypeClass()));
				specification = specification.and(buildSearchText(MediaTypeTblEnum.typeCode.name(), sco.getTypeCode()));
				specification = specification.and(buildSearchText(MediaTypeTblEnum.typeName.name(), sco.getTypeName()));
				specification = specification
						.and(buildSearchText(MediaTypeTblEnum.description.name(), sco.getDescription()));
				specification = specification
						.and(buildSearchBoolean(MediaTypeTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
			}
		}

		// Return result
		return specification;
	}
}