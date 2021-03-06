package com.hientran.sohebox.transformer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.FoodTbl;
import com.hientran.sohebox.vo.FoodVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Component
public class FoodTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert Page<FoodTbl> to PageResultVO<FoodVO>
     *
     * @param Page<FoodTbl>
     * 
     * @return PageResultVO<FoodVO>
     */
    public PageResultVO<FoodVO> convertToPageReturn(Page<FoodTbl> pageTbl) {
        // Declare result
        PageResultVO<FoodVO> result = new PageResultVO<FoodVO>();

        // Convert data
        if (CollectionUtils.isNotEmpty(pageTbl.getContent())) {
            List<FoodVO> listVO = new ArrayList<>();
            for (FoodTbl tbl : pageTbl.getContent()) {
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
    public FoodVO convertToVO(FoodTbl tbl) {
        // Declare result
        FoodVO result = new FoodVO();

        // Transformation
        objectMapper.map(tbl, result);

        if (tbl.getRecipe() != null) {
            result.setRecipeString(new String(tbl.getRecipe(), StandardCharsets.UTF_8));
        }

        // Return
        return result;
    }

    /**
     * Convert tbl to vo
     *
     * @param Food
     * @return
     */
    public FoodTbl convertToTbl(FoodVO vo) {
        // Declare result
        FoodTbl result = new FoodTbl();

        // Transformation
        objectMapper.map(vo, result);

        if (StringUtils.isNotBlank(vo.getRecipeString())) {
            result.setRecipe(vo.getRecipeString().getBytes(StandardCharsets.UTF_8));
        }

        // Return
        return result;
    }
}
