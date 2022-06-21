package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.RoleTblEnum;
import com.hientran.sohebox.entity.RoleTbl;
import com.hientran.sohebox.sco.RoleSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class RoleSpecs extends GenericSpecs {

    public Specification<RoleTbl> buildSpecification(RoleSCO sco) {
        // Declare result
        Specification<RoleTbl> specification = Specification.where(null);

        // Add criteria
        if (sco != null) {
            specification = specification.and(buildSearchText(RoleTblEnum.roleName.name(), sco.getRoleName()));
            specification = specification
                    .and(buildSearchDate(RoleTblEnum.createdDate.name(), sco.getCreatedDate(), true));
        }

        // Return result
        return specification;
    }
}