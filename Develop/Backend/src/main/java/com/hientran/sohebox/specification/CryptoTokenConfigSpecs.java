package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.CryptoTokenConfigTblEnum;
import com.hientran.sohebox.entity.CryptoTokenConfigTbl;
import com.hientran.sohebox.sco.CryptoTokenConfigSCO;

/**
 * @author hientran
 */
@Component
public class CryptoTokenConfigSpecs extends GenericSpecs<CryptoTokenConfigTbl> {

	public Specification<CryptoTokenConfigTbl> buildSpecification(CryptoTokenConfigSCO sco) {
		// Declare result
		Specification<CryptoTokenConfigTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification.or(buildSearchNumber(CryptoTokenConfigTblEnum.id.name(), sco.getId()));
				specification = specification
						.or(buildSearchText(CryptoTokenConfigTblEnum.tokenCode.name(), sco.getTokenCode()));
				specification = specification
						.or(buildSearchText(CryptoTokenConfigTblEnum.tokenName.name(), sco.getTokenName()));
				specification = specification
						.or(buildSearchText(CryptoTokenConfigTblEnum.denom.name(), sco.getDenom()));
				specification = specification
						.or(buildSearchText(CryptoTokenConfigTblEnum.nodeUrl.name(), sco.getNodeUrl()));
				specification = specification
						.or(buildSearchText(CryptoTokenConfigTblEnum.addressPrefix.name(), sco.getAddressPrefix()));
			} else {
				specification = specification.and(buildSearchNumber(CryptoTokenConfigTblEnum.id.name(), sco.getId()));
				specification = specification
						.and(buildSearchText(CryptoTokenConfigTblEnum.tokenCode.name(), sco.getTokenCode()));
				specification = specification
						.and(buildSearchText(CryptoTokenConfigTblEnum.tokenName.name(), sco.getTokenName()));
				specification = specification
						.and(buildSearchText(CryptoTokenConfigTblEnum.denom.name(), sco.getDenom()));
				specification = specification
						.and(buildSearchText(CryptoTokenConfigTblEnum.nodeUrl.name(), sco.getNodeUrl()));
				specification = specification
						.and(buildSearchText(CryptoTokenConfigTblEnum.addressPrefix.name(), sco.getAddressPrefix()));
			}
		}

		// Return result
		return specification;
	}
}