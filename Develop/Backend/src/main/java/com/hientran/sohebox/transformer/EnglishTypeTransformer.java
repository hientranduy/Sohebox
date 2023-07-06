package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.EnglishTypeTbl;
import com.hientran.sohebox.vo.EnglishTypeVO;
import com.hientran.sohebox.vo.PageResultVO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnglishTypeTransformer extends BaseTransformer {

	private final Mapper objectMapper;

	public PageResultVO<EnglishTypeVO> convertToPageReturn(Page<EnglishTypeTbl> pageTbl) {
		// Declare result
		PageResultVO<EnglishTypeVO> result = new PageResultVO<EnglishTypeVO>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<EnglishTypeVO> listVO = new ArrayList<>();
			for (EnglishTypeTbl tbl : pageTbl.getContent()) {
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

	public EnglishTypeVO convertToVO(EnglishTypeTbl tbl) {
		// Declare result
		EnglishTypeVO result = new EnglishTypeVO();

		// Transformation
		objectMapper.map(tbl, result);

		// Return
		return result;
	}

	public EnglishTypeTbl convertToTbl(EnglishTypeVO vo) {
		// Declare result
		EnglishTypeTbl result = new EnglishTypeTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
