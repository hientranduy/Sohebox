package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.EnglishLearnRecordTbl;
import com.hientran.sohebox.vo.EnglishLearnRecordVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Component
public class EnglishLearnRecordTransformer extends BaseTransformer {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("objectMapper")
	private Mapper objectMapper;

	/**
	 * Convert Page<EnglishLearnRecordTbl> to PageResultVO<EnglishLearnRecordVO>
	 *
	 * @param Page<EnglishLearnRecordTbl>
	 * 
	 * @return PageResultVO<EnglishLearnRecordVO>
	 */
	public PageResultVO<EnglishLearnRecordVO> convertToPageReturn(Page<EnglishLearnRecordTbl> pageTbl) {
		// Declare result
		PageResultVO<EnglishLearnRecordVO> result = new PageResultVO<EnglishLearnRecordVO>();

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
