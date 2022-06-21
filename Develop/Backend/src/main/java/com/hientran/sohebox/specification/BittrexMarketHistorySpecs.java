package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.BittrexMarketHistoryTblEnum;
import com.hientran.sohebox.entity.BittrexMarketHistoryTbl;
import com.hientran.sohebox.sco.BittrexMarketHistorySCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class BittrexMarketHistorySpecs extends GenericSpecs {

    public Specification<BittrexMarketHistoryTbl> buildSpecification(BittrexMarketHistorySCO sco) {
        // Declare result
        Specification<BittrexMarketHistoryTbl> specification = Specification.where(null);

        // Add criteria
        if (sco != null) {
            specification = specification.and(buildSearchText(BittrexMarketHistoryTblEnum.marketName.name(), sco.getMarketName()));
            specification = specification.and(buildSearchDate(BittrexMarketHistoryTblEnum.timeStamp.name(), sco.getTimeStamp(), true));
            specification = specification.and(buildSearchText(BittrexMarketHistoryTblEnum.orderType.name(), sco.getOrderType()));
        }

        // Return result
        return specification;
    }
}