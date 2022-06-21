package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.YoutubeChannelTbl;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.vo.YoutubeChannelVO;

/**
 * @author hientran
 */
@Component
public class YoutubeChannelTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert Page<YoutubeChannelTbl> to PageResultVO<YoutubeChannelVO>
     *
     * @param Page<YoutubeChannelTbl>
     * 
     * @return PageResultVO<YoutubeChannelVO>
     */
    public PageResultVO<YoutubeChannelVO> convertToPageReturn(Page<YoutubeChannelTbl> pageTbl) {
        // Declare result
        PageResultVO<YoutubeChannelVO> result = new PageResultVO<YoutubeChannelVO>();

        // Convert data
        if (CollectionUtils.isNotEmpty(pageTbl.getContent())) {
            List<YoutubeChannelVO> listVO = new ArrayList<>();
            for (YoutubeChannelTbl tbl : pageTbl.getContent()) {
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
    public YoutubeChannelVO convertToVO(YoutubeChannelTbl tbl) {
        // Declare result
        YoutubeChannelVO result = new YoutubeChannelVO();

        // Transformation
        objectMapper.map(tbl, result);

        // Return
        return result;
    }

    /**
     * Convert tbl to vo
     *
     * @param YoutubeChannel
     * @return
     */
    public YoutubeChannelTbl convertToTbl(YoutubeChannelVO vo) {
        // Declare result
        YoutubeChannelTbl result = new YoutubeChannelTbl();

        // Transformation
        objectMapper.map(vo, result);

        // Return
        return result;
    }
}
