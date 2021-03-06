package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.BittrexPairTbl;
import com.hientran.sohebox.vo.BittrexPairVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Component
public class BittrexPairTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert Page<BittrexPairTbl> to PageResultVO<BittrexPairVO>
     *
     * @param Page<BittrexPairTbl>
     * 
     * @return PageResultVO<BittrexPairVO>
     */
    public PageResultVO<BittrexPairVO> convertToPageReturn(Page<BittrexPairTbl> pageTbl) {
        // Declare result
        PageResultVO<BittrexPairVO> result = new PageResultVO<BittrexPairVO>();

        // Convert data
        if (CollectionUtils.isNotEmpty(pageTbl.getContent())) {
            List<BittrexPairVO> listVO = new ArrayList<>();
            for (BittrexPairTbl tbl : pageTbl.getContent()) {
                listVO.add(convertToBittrexPairVO(tbl));
            }

            // Set return list to result
            result.setElements(listVO);
        }

        // Set header information
        setPageHeader(pageTbl, result);

        // Return
        return result;
    }

    /**
     * Convert vo to tbl
     *
     * @return
     */
    public BittrexPairVO convertToBittrexPairVO(BittrexPairTbl tbl) {
        // Declare result
        BittrexPairVO result = new BittrexPairVO();

        // Transformation
        objectMapper.map(tbl, result);

        // Return
        return result;
    }

    /**
     * Convert tbl to vo
     *
     * @param
     * @return
     */
    public BittrexPairTbl convertToBittrexPairTbl(BittrexPairVO vo) {
        // Declare result
        BittrexPairTbl result = new BittrexPairTbl();

        // Transformation
        objectMapper.map(vo, result);

        // Return
        return result;
    }
}
