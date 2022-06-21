package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.YoutubeVideoTblEnum;
import com.hientran.sohebox.entity.YoutubeVideoTbl;
import com.hientran.sohebox.sco.YoutubeVideoSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class YoutubeVideoSpecs extends GenericSpecs {

    public Specification<YoutubeVideoTbl> buildSpecification(YoutubeVideoSCO sco) {
        // Declare result
        Specification<YoutubeVideoTbl> specification = Specification.where(null);

        // Add criteria
        if (sco != null) {
            specification = specification.and(buildSearchText(YoutubeVideoTblEnum.videoId.name(), sco.getVideoId()));
        }

        // Return result
        return specification;
    }
}