package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.MediaTypeTbl;
import com.hientran.sohebox.vo.MediaTypeVO;
import com.hientran.sohebox.vo.PageResultVO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MediaTypeTransformer extends BaseTransformer {

	public PageResultVO<MediaTypeVO> convertToPageReturn(Page<MediaTypeTbl> pageTbl) {
		// Declare result
		PageResultVO<MediaTypeVO> result = new PageResultVO<MediaTypeVO>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<MediaTypeVO> listVO = new ArrayList<>();
			for (MediaTypeTbl tbl : pageTbl.getContent()) {
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

	public MediaTypeVO convertToVO(MediaTypeTbl tbl) {
		MediaTypeVO result = new MediaTypeVO();
		BeanUtils.copyProperties(tbl, result);
		return result;
	}

	public MediaTypeTbl convertToTbl(MediaTypeVO vo) {
		MediaTypeTbl result = new MediaTypeTbl();
		BeanUtils.copyProperties(vo, result);
		return result;
	}
}
