package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.YoutubeChannelVideoTbl;
import com.hientran.sohebox.sco.YoutubeChannelVideoSCO;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class YoutubeChannelVideoSpecs extends GenericSpecs {
	public enum YoutubeChannelVideoTblEnum {
		channel, video, deleteFlag
	}

	public Specification<YoutubeChannelVideoTbl> buildSpecification(YoutubeChannelVideoSCO sco) {
		// Declare result
		Specification<YoutubeChannelVideoTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			specification = specification
					.and(buildSearchNumber(YoutubeChannelVideoTblEnum.channel.name(), sco.getChannelId()));
			specification = specification
					.and(buildSearchNumber(YoutubeChannelVideoTblEnum.video.name(), sco.getVideoId()));
			specification = specification
					.and(buildSearchBoolean(YoutubeChannelVideoTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
		}

		// Return result
		return specification;
	}
}