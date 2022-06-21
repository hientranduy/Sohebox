package com.hientran.sohebox.transformer;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.BittrexCryptoTbl;
import com.hientran.sohebox.vo.BittrexCryptoVO;;

/**
 * @author hientran
 */
@Component
public class BittrexCryptoTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert vo to tbl
     *
     * @return
     */
    public BittrexCryptoVO convertToBittrexCryptoVO(BittrexCryptoTbl tbl) {
        // Declare result
        BittrexCryptoVO result = new BittrexCryptoVO();

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
    public BittrexCryptoTbl convertToBittrexCryptoTbl(BittrexCryptoVO vo) {
        // Declare result
        BittrexCryptoTbl result = new BittrexCryptoTbl();

        // Transformation
        objectMapper.map(vo, result);

        // Return
        return result;
    }
}
