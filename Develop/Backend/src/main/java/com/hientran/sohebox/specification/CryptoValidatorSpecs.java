package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.CryptoValidatorTbl;
import com.hientran.sohebox.sco.CryptoValidatorSCO;

@Component
public class CryptoValidatorSpecs extends GenericSpecs<CryptoValidatorTbl> {
	public enum CryptoValidatorTblEnum {
		id, validatorAddress, validatorName, validatorWebsite, commissionRate, totalDeligated, syncDate
	}

	public Specification<CryptoValidatorTbl> buildSpecification(CryptoValidatorSCO sco) {
		// Declare result
		Specification<CryptoValidatorTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification.or(buildSearchNumber(CryptoValidatorTblEnum.id.name(), sco.getId()));
				specification = specification
						.or(buildSearchText(CryptoValidatorTblEnum.validatorAddress.name(), sco.getValidatorAddress()));
			} else {
				specification = specification.and(buildSearchNumber(CryptoValidatorTblEnum.id.name(), sco.getId()));
				specification = specification.and(
						buildSearchText(CryptoValidatorTblEnum.validatorAddress.name(), sco.getValidatorAddress()));
			}
		}

		// Return result
		return specification;
	}
}