package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.dto.EnglishUserGradeVO;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.entity.EnglishUserGradeTbl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnglishUserGradeTransformer extends BaseTransformer {

	private final Mapper objectMapper;

	/**
	 * Convert Page<EnglishUserGradeTbl> to PageResultVO<EnglishUserGradeVO>
	 *
	 * @param Page<EnglishUserGradeTbl>
	 *
	 * @return PageResultVO<EnglishUserGradeVO>
	 */
	public PageResultVO<EnglishUserGradeVO> convertToPageReturn(Page<EnglishUserGradeTbl> pageTbl) {
		// Declare result
		PageResultVO<EnglishUserGradeVO> result = new PageResultVO<>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<EnglishUserGradeVO> listVO = new ArrayList<>();
			for (EnglishUserGradeTbl tbl : pageTbl.getContent()) {
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
	public EnglishUserGradeVO convertToVO(EnglishUserGradeTbl tbl) {
		// Declare result
		EnglishUserGradeVO result = new EnglishUserGradeVO();

		// Transformation
		objectMapper.map(tbl, result);

		// Return
		return result;
	}

	/**
	 * Convert tbl to vo
	 */
	public EnglishUserGradeTbl convertToTbl(EnglishUserGradeVO vo) {
		// Declare result
		EnglishUserGradeTbl result = new EnglishUserGradeTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
