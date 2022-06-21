package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.BittrexPairTblEnum;
import com.hientran.sohebox.entity.BittrexPairTbl;
import com.hientran.sohebox.sco.BittrexPairSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class BittrexPairSpecs extends GenericSpecs {

    public Specification<BittrexPairTbl> buildSpecification(BittrexPairSCO sco) {
        // Declare result
        Specification<BittrexPairTbl> specification = Specification.where(null);

        // Add criteria
        if (sco != null) {
            specification = specification.and(buildSearchText(BittrexPairTblEnum.marketCurrency.name(), sco.getMarketCurrency()));
            specification = specification.and(buildSearchText(BittrexPairTblEnum.baseCurrency.name(), sco.getBaseCurrency()));
            specification = specification.and(buildSearchText(BittrexPairTblEnum.marketCurrencyLong.name(), sco.getMarketCurrencyLong()));
            specification = specification.and(buildSearchText(BittrexPairTblEnum.baseCurrencyLong.name(), sco.getBaseCurrencyLong()));
            specification = specification.and(buildSearchNumber(BittrexPairTblEnum.minTradeSize.name(), sco.getMinTradeSize()));
            specification = specification.and(buildSearchText(BittrexPairTblEnum.marketName.name(), sco.getMarketName()));
            specification = specification.and(buildSearchBoolean(BittrexPairTblEnum.active.name(), sco.getActive()));
            specification = specification.and(buildSearchBoolean(BittrexPairTblEnum.restricted.name(), sco.getRestricted()));
            specification = specification.and(buildSearchDate(BittrexPairTblEnum.created.name(), sco.getCreated(), true));
            specification = specification.and(buildSearchText(BittrexPairTblEnum.notice.name(), sco.getNotice()));
            specification = specification.and(buildSearchBoolean(BittrexPairTblEnum.sponsored.name(), sco.getSponsored()));
            specification = specification.and(buildSearchText(BittrexPairTblEnum.logoUrl.name(), sco.getLogoUrl()));
            specification = specification.and(buildSearchBoolean(BittrexPairTblEnum.extractDataFlag.name(), sco.getExtractDataFlag()));
            specification = specification.and(buildSearchBoolean(BittrexPairTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
        }

        // Return result
        return specification;
    }
}