package com.hientran.sohebox.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.cache.TypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.ResponseCode;
import com.hientran.sohebox.constants.enums.TradingSymbolTblEnum;
import com.hientran.sohebox.entity.CountryTbl;
import com.hientran.sohebox.entity.TradingSymbolTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.TradingSymbolRepository;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.sco.TradingSymbolSCO;
import com.hientran.sohebox.specification.TradingSymbolSpecs;
import com.hientran.sohebox.transformer.TradingSymbolTransformer;
import com.hientran.sohebox.transformer.TypeTransformer;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.vo.TradingSymbolVO;
import com.hientran.sohebox.vo.TypeVO;

import lombok.RequiredArgsConstructor;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TradingSymbolService extends BaseService {

	private final TradingSymbolRepository tradingSymbolRepository;
	private final TradingSymbolTransformer tradingSymbolTransformer;
	private final CountryService countryService;
	private final TypeCache typeCache;
	private final TypeTransformer typeTransformer;
	private final TradingSymbolSpecs tradingSymbolSpecs;

	/**
	 * 
	 * Create
	 * 
	 * @param vo
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(TradingSymbolVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Symbol
			if (StringUtils.isBlank(vo.getSymbol())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TradingSymbolTblEnum.symbol.name()));
			}

			// Name
			if (StringUtils.isBlank(vo.getName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TradingSymbolTblEnum.name.name()));
			}

			// Symbol type
			if (vo.getSymbolType() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TradingSymbolTblEnum.symbolType.name()));
			}

			// Country
			if (vo.getCountry() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TradingSymbolTblEnum.country.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check if record existed already
		if (result.getStatus() == null) {
			if (recordIsExisted(vo.getSymbol())) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "symbol <" + vo.getSymbol() + ">"));
			}
		}

		/////////////////////
		// Record new word //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			TradingSymbolTbl tbl = tradingSymbolTransformer.convertToTbl(vo);

			// Set symbol type
			TypeVO symbolType = typeCache.getType(DBConstants.TYPE_CLASS_TRADING_SYMBOL_TYPE,
					vo.getSymbolType().getTypeCode());
			tbl.setSymbolType(typeTransformer.convertToTbl(symbolType));

			// Set country
			CountryTbl country = countryService.get(vo.getCountry().getName());
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
		APIResponse<Object> result = new APIResponse<Object>();

		// Get data
		Page<TradingSymbolTbl> page = tradingSymbolRepository.findAll(sco);

		// Transformer
		PageResultVO<TradingSymbolVO> data = tradingSymbolTransformer.convertToPageReturn(page);

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
		Boolean result = false;

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

	/**
	 * Get trading symbol
	 * 
	 * @param sco
	 * @return
	 */
	public TradingSymbolTbl getTradingSymbol(String tradingSymbol) {
		// Declare result
		TradingSymbolTbl result = null;

		// Get data
		SearchTextVO symbolSearch = new SearchTextVO();
		symbolSearch.setEq(tradingSymbol);
		TradingSymbolSCO sco = new TradingSymbolSCO();
		sco.setSymbol(symbolSearch);

		Optional<TradingSymbolTbl> tbl = tradingSymbolRepository.findOne(tradingSymbolSpecs.buildSpecification(sco));

		// Transformer
		if (tbl.isPresent()) {
			result = tbl.get();
		}

		// Return
		return result;
	}
}
