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
public class CryptoPortfolioSpecs extends GenericSpecs<CryptoPortfolioTbl> {

	private static final long serialVersionUID = 1L;

	public Specification<CryptoPortfolioTbl> buildSpecification(CryptoPortfolioSCO sco) {
		// Declare result
		Specification<CryptoPortfolioTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification.or(buildSearchNumber(CryptoPortfolioTblEnum.id.name(), sco.getId()));
				specification = specification
						.or(buildSearchNumber(CryptoPortfolioTblEnum.token.name(), sco.getToken()));
				specification = specification
						.or(buildSearchText(CryptoPortfolioTblEnum.wallet.name(), sco.getWallet()));
				specification = specification
						.or(buildSearchText(CryptoPortfolioTblEnum.starname.name(), sco.getStarname()));
			} else {
				specification = specification.and(buildSearchNumber(CryptoPortfolioTblEnum.id.name(), sco.getId()));
				specification = specification
						.and(buildSearchNumber(CryptoPortfolioTblEnum.token.name(), sco.getToken()));
				specification = specification
						.and(buildSearchText(CryptoPortfolioTblEnum.wallet.name(), sco.getWallet()));
				specification = specification
						.and(buildSearchText(CryptoPortfolioTblEnum.starname.name(), sco.getStarname()));
			}

			if (sco.getUser() != null) {
				specification = specification.and(buildSearchNumber(CryptoPortfolioTblEnum.user.name(), sco.getUser()));
			}
		}

		// Return result
		return specification;
	}
}