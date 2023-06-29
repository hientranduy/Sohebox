package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.UserTblEnum;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.sco.UserSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class UserSpecs extends GenericSpecs {

	public Specification<UserTbl> buildSpecification(UserSCO sco) {
		// Declare result
		Specification<UserTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification.or(buildSearchText(UserTblEnum.username.name(), sco.getUserName()));
				specification = specification.or(buildSearchText(UserTblEnum.firstName.name(), sco.getFirstName()));
				specification = specification.or(buildSearchText(UserTblEnum.lastName.name(), sco.getLastName()));
			} else {
				specification = specification.and(buildSearchText(UserTblEnum.username.name(), sco.getUserName()));
				specification = specification.and(buildSearchText(UserTblEnum.firstName.name(), sco.getFirstName()));
				specification = specification.and(buildSearchText(UserTblEnum.lastName.name(), sco.getLastName()));
				specification = specification
						.and(buildSearchBoolean(UserTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
			}
		}

		// Return result
		return specification;
	}
}