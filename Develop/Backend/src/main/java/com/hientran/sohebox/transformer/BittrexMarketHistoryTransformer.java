package com.hientran.sohebox.transformer;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.BittrexMarketHistoryTbl;
import com.hientran.sohebox.vo.BittrexMarketHistoryVO;;

/**
 * @author hientran
 */
@Component
public class BittrexMarketHistoryTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert vo to tbl
     *
     * @return
     */
    public BittrexMarketHistoryVO convertToBittrexMarketHistoryVO(BittrexMarketHistoryTbl tbl) {
        // Declare result
        BittrexMarketHistoryVO result = new BittrexMarketHistoryVO();

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
    public BittrexMarketHistoryTbl convertToBittrexMarketHistoryTbl(BittrexMarketHistoryVO vo) {
        // Declare result
        BittrexMarketHistoryTbl result = new BittrexMarketHistoryTbl();

        // Transformation
        objectMapper.map(vo, result);

        // Return
        return result;
    }
}
