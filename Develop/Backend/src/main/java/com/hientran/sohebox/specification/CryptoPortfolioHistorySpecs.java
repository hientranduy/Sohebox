package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.CryptoPortfolioHistoryTbl;
import com.hientran.sohebox.sco.CryptoPortfolioHistorySCO;

@Component
public class CryptoPortfolioHistorySpecs extends GenericSpecs<CryptoPortfolioHistoryTbl> {
	public enum CryptoPortfolioHistoryTblEnum {
		id, timeStamp, user, token
	}

	public Specification<CryptoPortfolioHistoryTbl> buildSpecification(CryptoPortfolioHistorySCO sco) {
		// Declare result
		Specification<CryptoPortfolioHistoryTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification
						.or(buildSearchNumber(CryptoPortfolioHistoryTblEnum.id.name(), sco.getId()));
				specification = specification
						.or(buildSearchDate(CryptoPortfolioHistoryTblEnum.timeStamp.name(), sco.getTimeStamp(), true));
			} else {
				specification = specification
						.and(buildSearchNumber(CryptoPortfolioHistoryTblEnum.id.name(), sco.getId()));
				specification = specification
						.and(buildSearchDate(CryptoPortfolioHistoryTblEnum.timeStamp.name(), sco.getTimeStamp(), true));
			}

			if (sco.getUser() != null) {
				specification = specification
						.and(buildSearchNumber(CryptoPortfolioHistoryTblEnum.user.name(), sco.getUser()));
			}
		}

		// Return result
		return specification;
	}
}