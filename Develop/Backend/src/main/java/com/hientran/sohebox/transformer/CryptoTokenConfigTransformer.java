package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.CryptoTokenConfigTbl;
import com.hientran.sohebox.vo.CryptoTokenConfigVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Component
public class CryptoTokenConfigTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert Page<xxxTbl> to PageResultVO<xxxVO>
     *
     */
    public PageResultVO<CryptoTokenConfigVO> convertToPageReturn(Page<CryptoTokenConfigTbl> pageTbl) {
        // Declare result
        PageResultVO<CryptoTokenConfigVO> result = new PageResultVO<CryptoTokenConfigVO>();

        // Convert data
        if (CollectionUtils.isNotEmpty(pageTbl.getContent())) {
            List<CryptoTokenConfigVO> listVO = new ArrayList<>();
            for (CryptoTokenConfigTbl tbl : pageTbl.getContent()) {
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
    public CryptoTokenConfigVO convertToVO(CryptoTokenConfigTbl tbl) {
        // Declare result
        CryptoTokenConfigVO result = new CryptoTokenConfigVO();

        // Transformation
        objectMapper.map(tbl, result);

        // Return
        return result;
    }

    /**
     * Convert Tbl to VO
     */
    public CryptoTokenConfigTbl convertToTbl(CryptoTokenConfigVO vo) {
        // Declare result
        CryptoTokenConfigTbl result = new CryptoTokenConfigTbl();

        // Transformation
        objectMapper.map(vo, result);

        // Return
        return result;
    }
}
