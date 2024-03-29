package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.RequestExternalTbl;
import com.hientran.sohebox.sco.RequestExternalSCO;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RequestExternalSpecs extends GenericSpecs {
	public enum RequestExternalTblEnum {
		id, requestType, requestUrl, createdDate, note
	}

	public Specification<RequestExternalTbl> buildSpecification(RequestExternalSCO sco) {
		// Declare result
		Specification<RequestExternalTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification
						.or(buildSearchDate(RequestExternalTblEnum.createdDate.name(), sco.getCreatedDate(), false));
				specification = specification
						.or(buildSearchText(RequestExternalTblEnum.requestUrl.name(), sco.getRequestUrl()));
				specification = specification
						.or(buildSearchNumber(RequestExternalTblEnum.requestType.name(), sco.getRequestTypeId()));
				specification = specification.or(buildSearchText(RequestExternalTblEnum.note.name(), sco.getNote()));
			} else {
				specification = specification
						.and(buildSearchDate(RequestExternalTblEnum.createdDate.name(), sco.getCreatedDate(), false));
				specification = specification
						.and(buildSearchText(RequestExternalTblEnum.requestUrl.name(), sco.getRequestUrl()));
				specification = specification
						.and(buildSearchNumber(RequestExternalTblEnum.requestType.name(), sco.getRequestTypeId()));
				specification = specification.and(buildSearchText(RequestExternalTblEnum.note.name(), sco.getNote()));
			}
		}

		// Return result
		return specification;
	}
}