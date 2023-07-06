package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.vo.TypeVO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TypeTransformer extends BaseTransformer {

	private final Mapper objectMapper;

	public PageResultVO<TypeVO> convertToPageReturn(Page<TypeTbl> pageTbl) {
		// Declare result
		PageResultVO<TypeVO> result = new PageResultVO<TypeVO>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<TypeVO> listVO = new ArrayList<>();
			for (TypeTbl tbl : pageTbl.getContent()) {
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

	public TypeVO convertToVO(TypeTbl tbl) {
		// Declare result
		TypeVO result = new TypeVO();

		// Transformation
		objectMapper.map(tbl, result);

		// Return
		return result;
	}

	public TypeTbl convertToTbl(TypeVO vo) {
		// Declare result
		TypeTbl result = new TypeTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
