package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.EnglishLearnRecordTblEnum;
import com.hientran.sohebox.entity.EnglishLearnRecordTbl;
import com.hientran.sohebox.sco.EnglishLearnRecordSCO;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EnglishLearnRecordSpecs extends GenericSpecs {

	public Specification<EnglishLearnRecordTbl> buildSpecification(EnglishLearnRecordSCO sco) {
		// Declare result
		Specification<EnglishLearnRecordTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			specification = specification
					.and(buildSearchNumber(EnglishLearnRecordTblEnum.user.name(), sco.getUserId()));
			specification = specification
					.and(buildSearchNumber(EnglishLearnRecordTblEnum.english.name(), sco.getEnglishId()));
			specification = specification
					.and(buildSearchNumber(EnglishLearnRecordTblEnum.recordTimes.name(), sco.getRecordTimes()));
			specification = specification
					.and(buildSearchDate(EnglishLearnRecordTblEnum.updatedDate.name(), sco.getUpdatedDate(), true));
		}

		// Return result
		return specification;
	}
}