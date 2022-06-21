package com.hientran.sohebox.transformer;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.BittrexMarketSummary24hTbl;
import com.hientran.sohebox.vo.BittrexMarketSummaryVO;;

/**
 * @author hientran
 */
@Component
public class BittrexMarketSummary24hTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert vo to tbl
     *
     * @return
     */
    public BittrexMarketSummaryVO convertToBittrexMarketSummary24hVO(BittrexMarketSummary24hTbl tbl) {
        // Declare result
        BittrexMarketSummaryVO result = new BittrexMarketSummaryVO();

        // Transformation
        objectMapper.map(tbl, result);

        // Return
        return result;
    }

    /**
     * 
     * Convert tbl to vo
     *
     * @param vo
     * @return
     */
    public BittrexMarketSummary24hTbl convertToBittrexMarketSummary24hTbl(BittrexMarketSummaryVO vo) {
        // Declare result
        BittrexMarketSummary24hTbl result = new BittrexMarketSummary24hTbl();

        // Transformation
        objectMapper.map(vo, result);

        // Return
        return result;
    }
}
