package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.BittrexCryptoTblEnum;
import com.hientran.sohebox.entity.BittrexCryptoTbl;
import com.hientran.sohebox.sco.BittrexCryptoSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class BittrexCryptoSpecs extends GenericSpecs {

    public Specification<BittrexCryptoTbl> buildSpecification(BittrexCryptoSCO sco) {
        // Declare result
        Specification<BittrexCryptoTbl> specification = Specification.where(null);

        // Add criteria
        if (sco != null) {
            specification = specification.and(buildSearchBoolean(BittrexCryptoTblEnum.active.name(), sco.getActive()));
            specification = specification
                    .and(buildSearchBoolean(BittrexCryptoTblEnum.restricted.name(), sco.getRestricted()));
            specification = specification.and(buildSearchText(BittrexCryptoTblEnum.notice.name(), sco.getNotice()));
            specification = specification.and(buildSearchText(BittrexCryptoTblEnum.currency.name(), sco.getCurrency()));
            specification = specification
                    .and(buildSearchText(BittrexCryptoTblEnum.currencyLong.name(), sco.getCurrencyLong()));
            specification = specification
                    .and(buildSearchNumber(BittrexCryptoTblEnum.minConfirmation.name(), sco.getMinConfirmation()));
            specification = specification.and(buildSearchNumber(BittrexCryptoTblEnum.txFee.name(), sco.getTxFee()));
            specification = specification.and(buildSearchText(BittrexCryptoTblEnum.coinType.name(), sco.getCoinType()));
            specification = specification
                    .and(buildSearchText(BittrexCryptoTblEnum.baseAddress.name(), sco.getBaseAddress()));
            specification = specification
                    .and(buildSearchBoolean(BittrexCryptoTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
        }

        // Return result
        return specification;
    }
}