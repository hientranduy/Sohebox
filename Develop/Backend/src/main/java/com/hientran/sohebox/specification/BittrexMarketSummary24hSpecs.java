package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.BittrexMarketSummary24hTblEnum;
import com.hientran.sohebox.entity.BittrexMarketSummary24hTbl;
import com.hientran.sohebox.sco.BittrexMarketSummary24hSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class BittrexMarketSummary24hSpecs extends GenericSpecs {

    public Specification<BittrexMarketSummary24hTbl> buildSpecification(BittrexMarketSummary24hSCO sco) {
        // Declare result
        Specification<BittrexMarketSummary24hTbl> specification = Specification.where(null);

        // Add criteria
        if (sco != null) {
            specification = specification
                    .and(buildSearchText(BittrexMarketSummary24hTblEnum.marketName.name(), sco.getMarketName()));
            specification = specification
                    .and(buildSearchDate(BittrexMarketSummary24hTblEnum.timeStamp.name(), sco.getTimeStamp(), true));
        }

        // Return result
        return specification;
    }
}