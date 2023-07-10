package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.dto.EnglishLearnRecordVO;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.entity.EnglishLearnRecordTbl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnglishLearnRecordTransformer extends BaseTransformer {

	private final Mapper objectMapper;

	/**
	 * Convert Page<EnglishLearnRecordTbl> to PageResultVO<EnglishLearnRecordVO>
	 *
	 * @param Page<EnglishLearnRecordTbl>
	 *
	 * @return PageResultVO<EnglishLearnRecordVO>
	 */
	public PageResultVO<EnglishLearnRecordVO> convertToPageReturn(Page<EnglishLearnRecordTbl> pageTbl) {
		// Declare result
		PageResultVO<EnglishLearnRecordVO> result = new PageResultVO<>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<EnglishLearnRecordVO> listVO = new ArrayList<>();
			for (EnglishLearnRecordTbl tbl : pageTbl.getContent()) {
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
	public EnglishLearnRecordVO convertToVO(EnglishLearnRecordTbl tbl) {
		// Declare result
		EnglishLearnRecordVO result = new EnglishLearnRecordVO();

		// Transformation
		objectMapper.map(tbl, result);

		// Return
		return result;
	}

	/**
	 * Convert tbl to vo
	 */
	public EnglishLearnRecordTbl convertToTbl(EnglishLearnRecordVO vo) {
		// Declare result
		EnglishLearnRecordTbl result = new EnglishLearnRecordTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
