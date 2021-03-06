package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.EnglishLearnReportTbl;
import com.hientran.sohebox.vo.EnglishLearnReportVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Component
public class EnglishLearnReportTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert Page<EnglishLearnReportTbl> to PageResultVO<EnglishLearnReportVO>
     *
     * @param Page<EnglishLearnReportTbl>
     * 
     * @return PageResultVO<EnglishLearnReportVO>
     */
    public PageResultVO<EnglishLearnReportVO> convertToPageReturn(Page<EnglishLearnReportTbl> pageTbl) {
        // Declare result
        PageResultVO<EnglishLearnReportVO> result = new PageResultVO<EnglishLearnReportVO>();

        // Convert data
        if (CollectionUtils.isNotEmpty(pageTbl.getContent())) {
            List<EnglishLearnReportVO> listVO = new ArrayList<>();
            for (EnglishLearnReportTbl tbl : pageTbl.getContent()) {
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
     */
    public EnglishLearnReportVO convertToVO(EnglishLearnReportTbl tbl) {
        // Declare result
        EnglishLearnReportVO result = new EnglishLearnReportVO();

        // Transformation
        objectMapper.map(tbl, result);

        // Return
        return result;
    }

    /**
     * Convert tbl to vo
     */
    public EnglishLearnReportTbl convertToTbl(EnglishLearnReportVO vo) {
        // Declare result
        EnglishLearnReportTbl result = new EnglishLearnReportTbl();

        // Transformation
        objectMapper.map(vo, result);

        // Return
        return result;
    }
}
