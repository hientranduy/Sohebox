package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.UserActivityTbl;
import com.hientran.sohebox.sco.UserActivitySCO;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserActivitySpecs extends GenericSpecs {
	public enum UserActivityTblEnum {
		createdDate, user, activity
	}

	public Specification<UserActivityTbl> buildSpecification(UserActivitySCO sco) {
		// Declare result
		Specification<UserActivityTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			specification = specification
					.and(buildSearchDate(UserActivityTblEnum.createdDate.name(), sco.getCreatedDate(), true));
			specification = specification.and(buildSearchNumber(UserActivityTblEnum.user.name(), sco.getUserId()));
			specification = specification.and(buildSearchText(UserActivityTblEnum.activity.name(), sco.getActivity()));
		}

		// Return result
		return specification;
	}
}