package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.CryptoValidatorTbl;
import com.hientran.sohebox.vo.CryptoValidatorVO;
import com.hientran.sohebox.vo.PageResultVO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CryptoValidatorTransformer extends BaseTransformer {

	private final Mapper objectMapper;

	/**
	 * Convert Page<xxxTbl> to PageResultVO<xxxVO>
	 *
	 */
	public PageResultVO<CryptoValidatorVO> convertToPageReturn(Page<CryptoValidatorTbl> pageTbl) {
		// Declare result
		PageResultVO<CryptoValidatorVO> result = new PageResultVO<CryptoValidatorVO>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<CryptoValidatorVO> listVO = new ArrayList<>();
			for (CryptoValidatorTbl tbl : pageTbl.getContent()) {
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
	 * Convert VO to Tbl
	 *
	 * @return
	 */
	public CryptoValidatorVO convertToVO(CryptoValidatorTbl tbl) {
		// Declare result
		CryptoValidatorVO result = new CryptoValidatorVO();

		// Transformation
		objectMapper.map(tbl, result);

		// Return
		return result;
	}

	/**
	 * Convert Tbl to VO
	 */
	public CryptoValidatorTbl convertToTbl(CryptoValidatorVO vo) {
		// Declare result
		CryptoValidatorTbl result = new CryptoValidatorTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
