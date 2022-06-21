package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.MdpTblEnum;
import com.hientran.sohebox.entity.MdpTbl;
import com.hientran.sohebox.sco.MdpSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class MdpSpecs extends GenericSpecs {

    public Specification<MdpTbl> buildSpecification(MdpSCO sco) {
        // Declare result
        Specification<MdpTbl> specification = Specification.where(null);

        // Add criteria
        if (sco != null) {
            specification = specification.and(buildSearchText(MdpTblEnum.mdp.name(), sco.getMdp()));
        }

        // Return result
        return specification;
    }
}