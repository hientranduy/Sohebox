package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.EnglishLearnReportTblEnum;
import com.hientran.sohebox.entity.EnglishLearnReportTbl;
import com.hientran.sohebox.sco.EnglishLearnReportSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class EnglishLearnReportSpecs extends GenericSpecs {

    public Specification<EnglishLearnReportTbl> buildSpecification(EnglishLearnReportSCO sco) {
        // Declare result
        Specification<EnglishLearnReportTbl> specification = Specification.where(null);

        // Add criteria
        if (sco != null) {
            specification = specification
                    .and(buildSearchNumber(EnglishLearnReportTblEnum.user.name(), sco.getUserId()));
            specification = specification
                    .and(buildSearchDate(EnglishLearnReportTblEnum.learnedDate.name(), sco.getLearnedDate(), true));
        }

        // Return result
        return specification;
    }
}