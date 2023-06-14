package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.TradingSymbolTbl;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.vo.TradingSymbolVO;

/**
 * @author hientran
 */
@Component
public class TradingSymbolTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert Page<TradingSymbolTbl> to PageResultVO<TradingSymbolVO>
     *
     * @param Page<TradingSymbolTbl>
     * 
     * @return PageResultVO<TradingSymbolVO>
     */
    public PageResultVO<TradingSymbolVO> convertToPageReturn(Page<TradingSymbolTbl> pageTbl) {
        // Declare result
        PageResultVO<TradingSymbolVO> result = new PageResultVO<TradingSymbolVO>();

        // Convert data
        if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
            List<TradingSymbolVO> listVO = new ArrayList<>();
            for (TradingSymbolTbl tbl : pageTbl.getContent()) {
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
     * Convert vo to tbl
     *
     * @return
     */
    public TradingSymbolVO convertToVO(TradingSymbolTbl tbl) {
        // Declare result
        TradingSymbolVO result = new TradingSymbolVO();

        // Transformation
        objectMapper.map(tbl, result);

        // Return
        return result;
    }

    /**
     * Convert tbl to vo
     *
     * @param TradingSymbol
     * @return
     */
    public TradingSymbolTbl convertToTbl(TradingSymbolVO vo) {
        // Declare result
        TradingSymbolTbl result = new TradingSymbolTbl();

        // Transformation
        objectMapper.map(vo, result);

        // Return
        return result;
    }
}
