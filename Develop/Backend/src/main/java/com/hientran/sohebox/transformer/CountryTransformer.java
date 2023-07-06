package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.CountryTbl;
import com.hientran.sohebox.vo.CountryVO;
import com.hientran.sohebox.vo.PageResultVO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CountryTransformer extends BaseTransformer {

	private final Mapper objectMapper;

	/**
	 * Convert Page<CountryTbl> to PageResultVO<CountryVO>
	 *
	 * @param Page<CountryTbl>
	 * 
	 * @return PageResultVO<CountryVO>
	 */
	public PageResultVO<CountryVO> convertToPageReturn(Page<CountryTbl> pageTbl) {
		// Declare result
		PageResultVO<CountryVO> result = new PageResultVO<CountryVO>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<CountryVO> listVO = new ArrayList<>();
			for (CountryTbl tbl : pageTbl.getContent()) {
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
	public CountryVO convertToVO(CountryTbl tbl) {
		// Declare result
		CountryVO result = new CountryVO();

		// Transformation
		objectMapper.map(tbl, result);

		// Return
		return result;
	}

	/**
	 * Convert tbl to vo
	 *
	 * @param Country
	 * @return
	 */
	public CountryTbl convertToTbl(CountryVO vo) {
		// Declare result
		CountryTbl result = new CountryTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
