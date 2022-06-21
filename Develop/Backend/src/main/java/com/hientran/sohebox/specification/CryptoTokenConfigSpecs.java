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
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class CryptoTokenConfigSpecs extends GenericSpecs {
	public Specification<CryptoTokenConfigTbl> buildSpecification(CryptoTokenConfigSCO sco) {
		// Declare result
		Specification<CryptoTokenConfigTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification.or(buildSearchNumber(CryptoTokenConfigTblEnum.id.name(), sco.getId()));
				specification = specification
						.or(buildSearchText(CryptoTokenConfigTblEnum.tokenCode.name(), sco.getTokenCode()));
			} else {
				specification = specification.and(buildSearchNumber(CryptoTokenConfigTblEnum.id.name(), sco.getId()));
				specification = specification
						.and(buildSearchText(CryptoTokenConfigTblEnum.tokenCode.name(), sco.getTokenCode()));
			}
		}

		// Return result
		return specification;
	}
}