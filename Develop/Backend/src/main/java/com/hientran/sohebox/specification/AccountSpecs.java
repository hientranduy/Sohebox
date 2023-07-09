package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.AccountTbl;
import com.hientran.sohebox.sco.AccountSCO;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AccountSpecs extends GenericSpecs {
	public enum AccountTblEnum {
		id, deleteFlag, user, type, note, accountName
	}

	public Specification<AccountTbl> buildSpecification(AccountSCO sco) {
		// Declare result
		Specification<AccountTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification.or(buildSearchNumber(AccountTblEnum.id.name(), sco.getId()));
				specification = specification.or(buildSearchNumber(AccountTblEnum.type.name(), sco.getAccountType()));
				specification = specification
						.or(buildSearchText(AccountTblEnum.accountName.name(), sco.getAccountName()));
				specification = specification.or(buildSearchText(AccountTblEnum.note.name(), sco.getNote()));
				specification = specification
						.or(buildSearchBoolean(AccountTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
			} else {
				specification = specification.and(buildSearchNumber(AccountTblEnum.id.name(), sco.getId()));
				specification = specification.and(buildSearchNumber(AccountTblEnum.type.name(), sco.getAccountType()));
				specification = specification
						.and(buildSearchText(AccountTblEnum.accountName.name(), sco.getAccountName()));
				specification = specification.and(buildSearchText(AccountTblEnum.note.name(), sco.getNote()));
				specification = specification
						.and(buildSearchBoolean(AccountTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
			}

			if (sco.getUser() != null) {
				specification = specification.and(buildSearchNumber(AccountTblEnum.user.name(), sco.getUser()));
			}
		}

		// Return result
		return specification;
	}
}