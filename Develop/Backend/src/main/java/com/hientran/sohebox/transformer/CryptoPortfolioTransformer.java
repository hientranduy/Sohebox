package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.CryptoPortfolioTbl;
import com.hientran.sohebox.vo.CryptoPortfolioVO;
import com.hientran.sohebox.vo.PageResultVO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CryptoPortfolioTransformer extends BaseTransformer {

	private final Mapper objectMapper;

	/**
	 * Convert Page<xxxTbl> to PageResultVO<xxxVO>
	 *
	 */
	public PageResultVO<CryptoPortfolioVO> convertToPageReturn(Page<CryptoPortfolioTbl> pageTbl) {
		// Declare result
		PageResultVO<CryptoPortfolioVO> result = new PageResultVO<CryptoPortfolioVO>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<CryptoPortfolioVO> listVO = new ArrayList<>();
			for (CryptoPortfolioTbl tbl : pageTbl.getContent()) {
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
	public CryptoPortfolioVO convertToVO(CryptoPortfolioTbl tbl) {
		// Declare result
		CryptoPortfolioVO result = new CryptoPortfolioVO();

		// Transformation
		objectMapper.map(tbl, result);

		// Return
		return result;
	}

	/**
	 * Convert Tbl to VO
	 */
	public CryptoPortfolioTbl convertToTbl(CryptoPortfolioVO vo) {
		// Declare result
		CryptoPortfolioTbl result = new CryptoPortfolioTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
