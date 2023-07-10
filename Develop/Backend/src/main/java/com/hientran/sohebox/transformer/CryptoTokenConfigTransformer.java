package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.dto.CryptoTokenConfigVO;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.entity.CryptoTokenConfigTbl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CryptoTokenConfigTransformer extends BaseTransformer {

	private final Mapper objectMapper;

	/**
	 * Convert Page<xxxTbl> to PageResultVO<xxxVO>
	 *
	 */
	public PageResultVO<CryptoTokenConfigVO> convertToPageReturn(Page<CryptoTokenConfigTbl> pageTbl) {
		// Declare result
		PageResultVO<CryptoTokenConfigVO> result = new PageResultVO<>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<CryptoTokenConfigVO> listVO = new ArrayList<>();
			for (CryptoTokenConfigTbl tbl : pageTbl.getContent()) {
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
	public CryptoTokenConfigVO convertToVO(CryptoTokenConfigTbl tbl) {
		// Declare result
		CryptoTokenConfigVO result = new CryptoTokenConfigVO();

		// Transformation
		objectMapper.map(tbl, result);

		// Return
		return result;
	}

	/**
	 * Convert Tbl to VO
	 */
	public CryptoTokenConfigTbl convertToTbl(CryptoTokenConfigVO vo) {
		// Declare result
		CryptoTokenConfigTbl result = new CryptoTokenConfigTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
