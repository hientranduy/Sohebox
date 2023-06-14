package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.ConfigTbl;
import com.hientran.sohebox.vo.ConfigVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Component
public class ConfigTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert Page<ConfigTbl> to PageResultVO<ConfigVO>
     *
     * @param Page<ConfigTbl>
     * 
     * @return PageResultVO<ConfigVO>
     */
    public PageResultVO<ConfigVO> convertToPageReturn(Page<ConfigTbl> pageTbl) {
        // Declare result
        PageResultVO<ConfigVO> result = new PageResultVO<ConfigVO>();

        // Convert data
        if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
            List<ConfigVO> listVO = new ArrayList<>();
            for (ConfigTbl tbl : pageTbl.getContent()) {
                listVO.add(convertToConfigVO(tbl));
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
    public ConfigVO convertToConfigVO(ConfigTbl tbl) {
        // Declare result
        ConfigVO result = new ConfigVO();

        // Transformation
        objectMapper.map(tbl, result);

        // Return
        return result;
    }

    /**
     * Convert tbl to vo
     *
     * @param config
     * @return
     */
    public ConfigTbl convertToConfigTbl(ConfigVO vo) {
        // Declare result
        ConfigTbl result = new ConfigTbl();

        // Transformation
        objectMapper.map(vo, result);

        // Return
        return result;
    }
}
