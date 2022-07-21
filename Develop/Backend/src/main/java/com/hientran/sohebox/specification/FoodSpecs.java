package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.FoodTblEnum;
import com.hientran.sohebox.entity.FoodTbl;
import com.hientran.sohebox.sco.FoodSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class FoodSpecs extends GenericSpecs {

    public Specification<FoodTbl> buildSpecification(FoodSCO sco) {
        // Declare result
        Specification<FoodTbl> specification = Specification.where(null);

        // Add criteria
        if (sco != null) {
            if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
                specification = specification.or(buildSearchText(FoodTblEnum.name.name(), sco.getName()));
                specification = specification.or(buildSearchNumber(FoodTblEnum.type.name(), sco.getTypeId()));
                specification = specification.or(buildSearchNumber(FoodTblEnum.category.name(), sco.getCategoryId()));
                specification = specification
                        .or(buildSearchBoolean(FoodTblEnum.isFastFood.name(), sco.getIsFastFood()));
            } else {
                specification = specification.and(buildSearchText(FoodTblEnum.name.name(), sco.getName()));
                specification = specification.and(buildSearchNumber(FoodTblEnum.type.name(), sco.getTypeId()));
                specification = specification.and(buildSearchNumber(FoodTblEnum.category.name(), sco.getCategoryId()));
                specification = specification
                        .and(buildSearchBoolean(FoodTblEnum.isFastFood.name(), sco.getIsFastFood()));
            }
        }

        // Return result
        return specification;
    }
}