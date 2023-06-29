package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.EnglishUserGradeTblEnum;
import com.hientran.sohebox.entity.EnglishUserGradeTbl;
import com.hientran.sohebox.sco.EnglishUserGradeSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class EnglishUserGradeSpecs extends GenericSpecs {

	public Specification<EnglishUserGradeTbl> buildSpecification(EnglishUserGradeSCO sco) {
		// Declare result
		Specification<EnglishUserGradeTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			specification = specification.and(buildSearchNumber(EnglishUserGradeTblEnum.user.name(), sco.getUserId()));
			specification = specification
					.and(buildSearchNumber(EnglishUserGradeTblEnum.vus_grade_id.name(), sco.getVusGradeId()));
			specification = specification
					.and(buildSearchNumber(EnglishUserGradeTblEnum.learn_day_id.name(), sco.getLearnDayId()));
		}

		// Return result
		return specification;
	}
}