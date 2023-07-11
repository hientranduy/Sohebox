package com.hientran.sohebox.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.cache.TypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.CountryTbl;
import com.hientran.sohebox.entity.TradingSymbolTbl;
import com.hientran.sohebox.repository.TradingSymbolRepository;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.sco.TradingSymbolSCO;
import com.hientran.sohebox.specification.TradingSymbolSpecs.TradingSymbolTblEnum;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TradingSymbolService extends BaseService {

	private final TradingSymbolRepository tradingSymbolRepository;
	private final CountryService countryService;
	private final TypeCache typeCache;

	/**
	 *
	 * Create
	 *
	 * @param rq
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(TradingSymbolTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Symbol
			if (StringUtils.isBlank(rq.getSymbol())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TradingSymbolTblEnum.symbol.name()));
			}

			// Name
			if (StringUtils.isBlank(rq.getName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TradingSymbolTblEnum.name.name()));
			}

			// Symbol type
			if (rq.getSymbolType() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TradingSymbolTblEnum.symbolType.name()));
			}

			// Country
			if (rq.getCountry() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TradingSymbolTblEnum.country.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check if record existed already
		if (result.getStatus() == null) {
			if (recordIsExisted(rq.getSymbol())) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "symbol <" + rq.getSymbol() + ">"));
			}
		}

		/////////////////////
		// Record new word //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			TradingSymbolTbl tbl = rq;

			// Set symbol type
			tbl.setSymbolType(
					typeCache.getType(DBConstants.TYPE_CLASS_TRADING_SYMBOL_TYPE, rq.getSymbolType().getTypeCode()));

			// Set country
			CountryTbl country = countryService.get(rq.getCountry().getName());
			tbl.setCountry(country);

			// Create User
			tbl = tradingSymbolRepository.save(tbl);

			// Set id return
			result.setData(tbl.getId());

		}

		// Return
		return result;
	}

	/**
	 * Search
	 *
	 * @param sco
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> search(TradingSymbolSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get data
		Page<TradingSymbolTbl> page = tradingSymbolRepository.findAll(sco);

		// Transformer
		PageResultVO<TradingSymbolTbl> data = new PageResultVO<>();
		if (!CollectionUtils.isEmpty(page.getContent())) {
			data.setElements(page.getContent());
			setPageHeader(page, data);
		}

		// Set data return
		result.setData(data);

		// Write activity type "TradingSymbol access"
		recordUserActivity(DBConstants.USER_ACTIVITY_ENGLISH_ACCESS);

		// Return
		return result;
	}

	/**
	 *
	 * Check if record is existed
	 *
	 * @param keyWord
	 * @return
	 */
	private boolean recordIsExisted(String symbol) {
		// Declare result
		boolean result = false;

		SearchTextVO symbolSearch = new SearchTextVO();
		symbolSearch.setEq(symbol);

		TradingSymbolSCO sco = new TradingSymbolSCO();
		sco.setSymbol(symbolSearch);

		// Get data
		List<TradingSymbolTbl> list = tradingSymbolRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			result = true;
		}

		// Return
		return result;
	}
}
