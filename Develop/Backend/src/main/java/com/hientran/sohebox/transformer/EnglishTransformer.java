package com.hientran.sohebox.transformer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.EnglishTbl;
import com.hientran.sohebox.vo.EnglishTypeVO;
import com.hientran.sohebox.vo.EnglishVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Component
public class EnglishTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert Page<EnglishTbl> to PageResultVO<EnglishVO>
     *
     * @param Page<EnglishTbl>
     * 
     * @return PageResultVO<EnglishVO>
     */
    public PageResultVO<EnglishVO> convertToPageReturn(Page<EnglishTbl> pageTbl) {
        // Declare result
        PageResultVO<EnglishVO> result = new PageResultVO<EnglishVO>();

        // Convert data
        if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
            List<EnglishVO> listVO = new ArrayList<>();
            for (EnglishTbl tbl : pageTbl.getContent()) {
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
    public EnglishVO convertToVO(EnglishTbl tbl) {
        // Declare result
        EnglishVO result = new EnglishVO();

        // Transformation
        objectMapper.map(tbl, result);

        // Return
        return result;
    }

    /**
     * Convert vo to tbl
     *
     * @return
     */
    public EnglishVO convertToVO(Object[] objects) {
        // Declare result
        EnglishVO result = new EnglishVO();

        ////////////////////
        // Transformation //
        ////////////////////

        // id
        if (objects[0] != null) {
            result.setId((Long) objects[0]);
        }

        // Keyword
        if (objects[1] != null) {
            result.setKeyWord((String) objects[1]);
        }

        // Image name
        if (objects[2] != null) {
            result.setImageName((String) objects[2]);
        }

        // Explanation en
        if (objects[3] != null) {
            result.setExplanationEn((String) objects[3]);
        }

        // Explanation vn
        if (objects[4] != null) {
            result.setExplanationVn((String) objects[4]);
        }

        // Voice Uk file name
        if (objects[5] != null) {
            result.setVoiceUkFile((String) objects[5]);
        }

        // Voice Us file name
        if (objects[6] != null) {
            result.setVoiceUsFile((String) objects[6]);
        }

        // Record time
        if (objects[7] != null) {
            BigInteger recordTimes = (BigInteger) objects[7];
            result.setRecordTimes(recordTimes.longValue());
        }

        // Category code
        if (objects[8] != null) {
            EnglishTypeVO category = new EnglishTypeVO();
            category.setTypeCode((String) objects[8]);
            result.setCategory(category);
        }

        // grade code
        if (objects[9] != null) {
            EnglishTypeVO vusGrade = new EnglishTypeVO();
            vusGrade.setTypeCode((String) objects[9]);
            result.setVusGrade(vusGrade);
        }

        // Return
        return result;
    }

    /**
     * Convert tbl to vo
     *
     * @param English
     * @return
     */
    public EnglishTbl convertToTbl(EnglishVO vo) {
        // Declare result
        EnglishTbl result = new EnglishTbl();

        // Transformation
        objectMapper.map(vo, result);

        // Return
        return result;
    }
}
