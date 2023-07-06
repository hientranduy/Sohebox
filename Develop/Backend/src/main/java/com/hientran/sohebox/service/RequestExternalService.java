package com.hientran.sohebox.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.cache.TypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.entity.RequestExternalTbl;
import com.hientran.sohebox.repository.RequestExternalRepository;
import com.hientran.sohebox.sco.RequestExternalSCO;
import com.hientran.sohebox.transformer.RequestExternalTransformer;
import com.hientran.sohebox.transformer.TypeTransformer;
import com.hientran.sohebox.vo.RequestExternalVO;
import com.hientran.sohebox.vo.TypeVO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestExternalService extends BaseService {

	private final RequestExternalRepository requestExternalRepository;
	private final RequestExternalTransformer requestExternalTransformer;
	private final TypeCache typeCache;
	private final TypeTransformer typeTransformer;

	/**
	 * 
	 * Create
	 * 
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void create(RequestExternalVO vo) throws Exception {

		// Transform
		RequestExternalTbl tbl = requestExternalTransformer.convertToTbl(vo);

		tbl.setCreatedDate(new Date());
		tbl.setNote(vo.getNote());
		tbl.setRequestUrl(vo.getRequestUrl());
		TypeVO requestType = typeCache.getType(DBConstants.TYPE_CLASS_REQUEST_EXTERNAL_TYPE,
				vo.getRequestType().getTypeCode());
		tbl.setRequestType(typeTransformer.convertToTbl(requestType));

		requestExternalRepository.save(tbl);
	}

	/**
	 * Search
	 * 
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<RequestExternalTbl> search(RequestExternalSCO sco) {
		// Declare result
		List<RequestExternalTbl> result = null;

		// Get data
		Page<RequestExternalTbl> page = requestExternalRepository.findAll(sco);

		// Transformer
		if (page.getContent() != null && CollectionUtils.isNotEmpty(page.getContent())) {
			result = page.getContent();
		}

		// Return
		return result;
	}

}
