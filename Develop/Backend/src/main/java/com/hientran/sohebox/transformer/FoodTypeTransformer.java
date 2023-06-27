package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.FoodTypeTbl;
import com.hientran.sohebox.vo.FoodTypeVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Component
public class FoodTypeTransformer extends BaseTransformer {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("objectMapper")
	private Mapper objectMapper;

	/**
	 * Convert Page<FoodTypeTbl> to PageResultVO<FoodTypeVO>
	 *
	 * @param Page<FoodTypeTbl>
	 * 
	 * @return PageResultVO<FoodTypeVO>
	 */
	public PageResultVO<FoodTypeVO> convertToPageReturn(Page<FoodTypeTbl> pageTbl) {
		// Declare result
		PageResultVO<FoodTypeVO> result = new PageResultVO<FoodTypeVO>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<FoodTypeVO> listVO = new ArrayList<>();
			for (FoodTypeTbl tbl : pageTbl.getContent()) {
				listVO.add(convertToFoodTypeVO(tbl));
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
	public FoodTypeVO convertToFoodTypeVO(FoodTypeTbl tbl) {
		// Declare result
		FoodTypeVO result = new FoodTypeVO();

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
	public FoodTypeTbl convertToFoodTypeTbl(FoodTypeVO vo) {
		// Declare result
		FoodTypeTbl result = new FoodTypeTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
