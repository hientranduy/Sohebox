package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.TypeTblEnum;
import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.sco.TypeSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class TypeSpecs extends GenericSpecs {

    public Specification<TypeTbl> buildSpecification(TypeSCO sco) {
        // Declare result
        Specification<TypeTbl> specification = Specification.where(null);

        // Add criteria
        if (sco != null) {
            if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
            specification = specification.or(buildSearchText(TypeTblEnum.typeClass.name(), sco.getTypeClass()));
            specification = specification.or(buildSearchText(TypeTblEnum.typeCode.name(), sco.getTypeCode()));
            specification = specification.or(buildSearchText(TypeTblEnum.typeName.name(), sco.getTypeName()));
            specification = specification.or(buildSearchText(TypeTblEnum.description.name(), sco.getDescription()));
            specification = specification.or(buildSearchBoolean(TypeTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
            } else {
            specification = specification.and(buildSearchText(TypeTblEnum.typeClass.name(), sco.getTypeClass()));
            specification = specification.and(buildSearchText(TypeTblEnum.typeCode.name(), sco.getTypeCode()));
            specification = specification.and(buildSearchText(TypeTblEnum.typeName.name(), sco.getTypeName()));
            specification = specification.and(buildSearchText(TypeTblEnum.description.name(), sco.getDescription()));
            specification = specification.and(buildSearchBoolean(TypeTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
            }
        }

        // Return result
        return specification;
    }
}