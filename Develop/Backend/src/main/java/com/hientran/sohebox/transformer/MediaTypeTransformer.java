package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.MediaTypeTbl;
import com.hientran.sohebox.vo.MediaTypeVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Component
public class MediaTypeTransformer extends BaseTransformer {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("objectMapper")
    private Mapper objectMapper;

    /**
     * Convert Page<MediaTypeTbl> to PageResultVO<MediaTypeVO>
     *
     * @param Page<MediaTypeTbl>
     * 
     * @return PageResultVO<MediaTypeVO>
     */
    public PageResultVO<MediaTypeVO> convertToPageReturn(Page<MediaTypeTbl> pageTbl) {
        // Declare result
        PageResultVO<MediaTypeVO> result = new PageResultVO<MediaTypeVO>();

        // Convert data
        if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
            List<MediaTypeVO> listVO = new ArrayList<>();
            for (MediaTypeTbl tbl : pageTbl.getContent()) {
                listVO.add(convertToMediaTypeVO(tbl));
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
    public MediaTypeVO convertToMediaTypeVO(MediaTypeTbl tbl) {
        // Declare result
        MediaTypeVO result = new MediaTypeVO();

        // Transformation
        objectMapper.map(tbl, result);

        // Return
        return result;
    }

    /**
     * Convert tbl to vo
     *
     * @param type
     * @return
     */
    public MediaTypeTbl convertToMediaTypeTbl(MediaTypeVO vo) {
        // Declare result
        MediaTypeTbl result = new MediaTypeTbl();

        // Transformation
        objectMapper.map(vo, result);

        // Return
        return result;
    }
}
