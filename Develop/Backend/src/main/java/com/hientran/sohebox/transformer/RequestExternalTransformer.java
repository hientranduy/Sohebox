package com.hientran.sohebox.transformer;

import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.entity.RequestExternalTbl;
import com.hientran.sohebox.vo.RequestExternalVO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestExternalTransformer extends BaseTransformer {

	private final Mapper objectMapper;

	/**
	 * Convert vo to tbl
	 *
	 * @return
	 */
	public RequestExternalVO convertToVO(RequestExternalTbl tbl) {
		// Declare result
		RequestExternalVO result = new RequestExternalVO();

		// Transformation
		objectMapper.map(tbl, result);

		// Return
		return result;
	}

	/**
	 * Convert tbl to vo
	 *
	 * @param requestExternal
	 * @return
	 */
	public RequestExternalTbl convertToTbl(RequestExternalVO vo) {
		// Declare result
		RequestExternalTbl result = new RequestExternalTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
