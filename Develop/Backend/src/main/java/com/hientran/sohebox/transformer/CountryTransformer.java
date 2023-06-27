package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.CountryTbl;
import com.hientran.sohebox.vo.CountryVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Component
public class CountryTransformer extends BaseTransformer {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("objectMapper")
	private Mapper objectMapper;

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
