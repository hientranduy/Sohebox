package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.YoutubeChannelTblEnum;
import com.hientran.sohebox.entity.YoutubeChannelTbl;
import com.hientran.sohebox.sco.YoutubeChannelSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class YoutubeChannelSpecs extends GenericSpecs {

	public Specification<YoutubeChannelTbl> buildSpecification(YoutubeChannelSCO sco) {
		// Declare result
		Specification<YoutubeChannelTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification
						.or(buildSearchText(YoutubeChannelTblEnum.channelId.name(), sco.getChannelId()));
				specification = specification.or(buildSearchText(YoutubeChannelTblEnum.name.name(), sco.getName()));
				specification = specification
						.or(buildSearchText(YoutubeChannelTblEnum.description.name(), sco.getDescription()));
				specification = specification
						.or(buildSearchNumber(YoutubeChannelTblEnum.category.name(), sco.getCategoryId()));
				specification = specification.or(buildSearchNumber(YoutubeChannelTblEnum.user.name(), sco.getUserId()));
			} else {
				specification = specification
						.and(buildSearchText(YoutubeChannelTblEnum.channelId.name(), sco.getChannelId()));
				specification = specification.and(buildSearchText(YoutubeChannelTblEnum.name.name(), sco.getName()));
				specification = specification
						.and(buildSearchText(YoutubeChannelTblEnum.description.name(), sco.getDescription()));
				specification = specification
						.and(buildSearchNumber(YoutubeChannelTblEnum.category.name(), sco.getCategoryId()));
				specification = specification
						.and(buildSearchNumber(YoutubeChannelTblEnum.user.name(), sco.getUserId()));
			}
		}

		// Return result
		return specification;
	}
}