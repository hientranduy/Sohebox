package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.TradingSymbolTblEnum;
import com.hientran.sohebox.entity.TradingSymbolTbl;
import com.hientran.sohebox.sco.TradingSymbolSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class TradingSymbolSpecs extends GenericSpecs {

    public Specification<TradingSymbolTbl> buildSpecification(TradingSymbolSCO sco) {
        // Declare result
        Specification<TradingSymbolTbl> specification = Specification.where(null);

        // Add criteria
        if (sco != null) {
            specification = specification.and(buildSearchText(TradingSymbolTblEnum.symbol.name(), sco.getSymbol()));
            specification = specification.and(buildSearchText(TradingSymbolTblEnum.name.name(), sco.getName()));
            specification = specification.and(buildSearchNumber(TradingSymbolTblEnum.symbolType.name(), sco.getSymbolType()));
            specification = specification.and(buildSearchNumber(TradingSymbolTblEnum.zone.name(), sco.getZone()));
            specification = specification.and(buildSearchNumber(TradingSymbolTblEnum.country.name(), sco.getCountry()));
            specification = specification.and(buildSearchText(TradingSymbolTblEnum.description.name(), sco.getDescription()));
            specification = specification.and(buildSearchBoolean(TradingSymbolTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
        }

        // Return result
        return specification;
    }
}