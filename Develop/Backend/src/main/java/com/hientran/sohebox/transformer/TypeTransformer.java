package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.vo.TypeVO;

/**
 * @author hientran
 */
@Component
public class TypeTransformer extends BaseTransformer {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("objectMapper")
	private Mapper objectMapper;

	/**
	 * Convert Page<TypeTbl> to PageResultVO<TypeVO>
	 *
	 * @param Page<TypeTbl>
	 * 
	 * @return PageResultVO<TypeVO>
	 */
	public PageResultVO<TypeVO> convertToPageReturn(Page<TypeTbl> pageTbl) {
		// Declare result
		PageResultVO<TypeVO> result = new PageResultVO<TypeVO>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<TypeVO> listVO = new ArrayList<>();
			for (TypeTbl tbl : pageTbl.getContent()) {
				listVO.add(convertToTypeVO(tbl));
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
	public TypeVO convertToTypeVO(TypeTbl tbl) {
		// Declare result
		TypeVO result = new TypeVO();

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
	public TypeTbl convertToTypeTbl(TypeVO vo) {
		// Declare result
		TypeTbl result = new TypeTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
