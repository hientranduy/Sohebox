package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.CryptoPortfolioHistoryTbl;
import com.hientran.sohebox.vo.CryptoPortfolioHistoryVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Component
public class CryptoPortfolioHistoryTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert Page<xxxTbl> to PageResultVO<xxxVO>
     *
     */
    public PageResultVO<CryptoPortfolioHistoryVO> convertToPageReturn(Page<CryptoPortfolioHistoryTbl> pageTbl) {
        // Declare result
        PageResultVO<CryptoPortfolioHistoryVO> result = new PageResultVO<CryptoPortfolioHistoryVO>();

        // Convert data
        if (CollectionUtils.isNotEmpty(pageTbl.getContent())) {
            List<CryptoPortfolioHistoryVO> listVO = new ArrayList<>();
            for (CryptoPortfolioHistoryTbl tbl : pageTbl.getContent()) {
                listVO.add(convertToVO(tbl));
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
     * Convert VO to Tbl
     *
     * @return
     */
    public CryptoPortfolioHistoryVO convertToVO(CryptoPortfolioHistoryTbl tbl) {
        // Declare result
        CryptoPortfolioHistoryVO result = new CryptoPortfolioHistoryVO();

        // Transformation
        objectMapper.map(tbl, result);

        // Return
        return result;
    }

    /**
     * Convert Tbl to VO
     */
    public CryptoPortfolioHistoryTbl convertToTbl(CryptoPortfolioHistoryVO vo) {
        // Declare result
        CryptoPortfolioHistoryTbl result = new CryptoPortfolioHistoryTbl();

        // Transformation
        objectMapper.map(vo, result);

        // Return
        return result;
    }
}
