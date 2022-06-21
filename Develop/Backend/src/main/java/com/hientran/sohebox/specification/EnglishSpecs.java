package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.EnglishTblEnum;
import com.hientran.sohebox.entity.EnglishTbl;
import com.hientran.sohebox.sco.EnglishSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class EnglishSpecs extends GenericSpecs {

    public Specification<EnglishTbl> buildSpecification(EnglishSCO sco) {
        // Declare result
        Specification<EnglishTbl> specification = Specification.where(null);

        // Add criteria
        if (sco != null) {
            if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
            specification = specification.or(buildSearchText(EnglishTblEnum.keyWord.name(), sco.getKeyWord()));
            specification = specification.or(buildSearchNumber(EnglishTblEnum.wordLevel.name(), sco.getWordLevelId()));
            specification = specification.or(buildSearchNumber(EnglishTblEnum.category.name(), sco.getCategoryId()));
            specification = specification.or(buildSearchNumber(EnglishTblEnum.vusGrade.name(), sco.getVusGradeId()));
            specification = specification.or(buildSearchNumber(EnglishTblEnum.learnDay.name(), sco.getLearnDayId()));
            } else {
            specification = specification.and(buildSearchText(EnglishTblEnum.keyWord.name(), sco.getKeyWord()));
            specification = specification.and(buildSearchNumber(EnglishTblEnum.wordLevel.name(), sco.getWordLevelId()));
            specification = specification.and(buildSearchNumber(EnglishTblEnum.category.name(), sco.getCategoryId()));
            specification = specification.and(buildSearchNumber(EnglishTblEnum.vusGrade.name(), sco.getVusGradeId()));
            specification = specification.and(buildSearchNumber(EnglishTblEnum.learnDay.name(), sco.getLearnDayId()));
            }
        }

        // Return result
        return specification;
    }
}