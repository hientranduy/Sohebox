package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.CryptoPortfolioTblEnum;
import com.hientran.sohebox.entity.CryptoPortfolioTbl;
import com.hientran.sohebox.sco.CryptoPortfolioSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class CryptoPortfolioSpecs extends GenericSpecs {
	public Specification<CryptoPortfolioTbl> buildSpecification(CryptoPortfolioSCO sco) {
		// Declare result
		Specification<CryptoPortfolioTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification.or(buildSearchNumber(CryptoPortfolioTblEnum.id.name(), sco.getId()));
				specification = specification.or(buildSearchNumber(CryptoPortfolioTblEnum.user.name(), sco.getUser()));
				specification = specification
						.or(buildSearchNumber(CryptoPortfolioTblEnum.token.name(), sco.getToken()));
			} else {
				specification = specification.and(buildSearchNumber(CryptoPortfolioTblEnum.id.name(), sco.getId()));
				specification = specification.and(buildSearchNumber(CryptoPortfolioTblEnum.user.name(), sco.getUser()));
				specification = specification
						.and(buildSearchNumber(CryptoPortfolioTblEnum.token.name(), sco.getToken()));
			}
		}

		// Return result
		return specification;
	}
}