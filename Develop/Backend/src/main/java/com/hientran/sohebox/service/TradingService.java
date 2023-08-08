package com.hientran.sohebox.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hientran.sohebox.cache.ConfigCache;
import com.hientran.sohebox.cache.TypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.DataExternalConstants;
import com.hientran.sohebox.constants.TradingConstants;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.TradingHistoryItemVO;
import com.hientran.sohebox.dto.TradingOilPriceSendVO;
import com.hientran.sohebox.dto.TradingStockPriceSendVO;
import com.hientran.sohebox.dto.TradingSymbolItemVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.CountryTbl;
import com.hientran.sohebox.entity.TradingSymbolTbl;
import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.repository.TradingSymbolRepository;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.TradingSymbolSCO;
import com.hientran.sohebox.utils.FileUtils;
import com.hientran.sohebox.utils.MyDateUtils;
import com.hientran.sohebox.utils.ObjectMapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradingService extends BaseService {

	private final TradingSymbolRepository tradingSymbolRepository;
	private final TypeCache typeCache;
	private final ConfigCache configCache;
	private final ObjectMapperUtil objectMapperUtil;
	private final RestTemplateService restTemplateService;

	@Value("${resource.path}")
	private String resourcePath;

	/**
	 * Search video
	 *
	 * @param sco
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> searchOilPrice() {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Calculate start date
		Date startDate = MyDateUtils.addMinusDate(new Date(), -7);

		// Prepare symbol
		List<String> symbols = new ArrayList<>();
		symbols.add(TradingConstants.TRADINGECONOMICS_SYMBOL_COM_CLI);
		symbols.add(TradingConstants.TRADINGECONOMICS_SYMBOL_COM_CO1);

		///////////////////
		// Get data LIVE //
		///////////////////
		Map<String, String> parameters = new HashMap<>();
		parameters.put(TradingConstants.TRADINGECONOMICS_PARAM_KEY, TradingConstants.TRADINGECONOMICS_CONSTANT_KEY);
		parameters.put(TradingConstants.TRADINGECONOMICS_PARAM_OUTPUT_FORMAT,
				TradingConstants.TRADINGECONOMICS_CONSTANT_FORMAT_JSON);

		List<TradingSymbolItemVO> symbolItems = null;
		String localFilepath = null;
		int lateTimeSecond = 0;
		try {
			localFilepath = resourcePath + DataExternalConstants.REQUEST_DATA_FILE_PATH_TRADING_MARKET_OIL;
			lateTimeSecond = Integer.parseInt(
					configCache.getValueByKey(DataExternalConstants.FINANCE_TRADING_MARKET_OIL_LATE_TIME_SECOND));

			String responseData = tradingWebServiceGet(TradingConstants.TRADINGECONOMICS_API_MARKET_SYMBOL, symbols,
					parameters, lateTimeSecond, localFilepath);
			TradingSymbolItemVO[] response = objectMapperUtil.readValue(responseData,
					new TypeReference<TradingSymbolItemVO[]>() {
					});
			if (response != null) {
				symbolItems = Arrays.asList(response);
			}
		} catch (Exception e) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
		}

		//////////////////////
		// Get data HISTORY //
		//////////////////////
		List<TradingHistoryItemVO> historyItems = null;
		localFilepath = null;
		lateTimeSecond = 0;
		if (result.getStatus() == null) {
			parameters = new HashMap<>();
			parameters.put(TradingConstants.TRADINGECONOMICS_PARAM_KEY, TradingConstants.TRADINGECONOMICS_CONSTANT_KEY);
			parameters.put(TradingConstants.TRADINGECONOMICS_PARAM_OUTPUT_FORMAT,
					TradingConstants.TRADINGECONOMICS_CONSTANT_FORMAT_JSON);
			parameters.put(TradingConstants.TRADINGECONOMICS_PARAM_START_DATE,
					MyDateUtils.formatDate(startDate, MyDateUtils.YYYYMMDD));

			try {
				localFilepath = resourcePath + DataExternalConstants.REQUEST_DATA_FILE_PATH_TRADING_HISTORY_OIL;
				lateTimeSecond = Integer.parseInt(
						configCache.getValueByKey(DataExternalConstants.FINANCE_TRADING_HISTORY_OIL_LATE_TIME_SECOND));

				String responseData = tradingWebServiceGet(TradingConstants.TRADINGECONOMICS_API_MARKET_HISTORY,
						symbols, parameters, lateTimeSecond, localFilepath);
				TradingHistoryItemVO[] response = objectMapperUtil.readValue(responseData,
						new TypeReference<TradingHistoryItemVO[]>() {
						});
				if (response != null) {
					historyItems = Arrays.asList(response);
				}
			} catch (Exception e) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
			}
		}

		////////////////////
		// Transform data //
		////////////////////
		if (result.getStatus() == null) {
			TradingSymbolItemVO symbolCL1 = null;
			TradingSymbolItemVO symbolCO1 = null;
			for (TradingSymbolItemVO item : symbolItems) {
				if (StringUtils.equals(item.getSymbol(), TradingConstants.TRADINGECONOMICS_SYMBOL_COM_CLI)) {
					symbolCL1 = item;
				} else {
					symbolCO1 = item;
				}
			}

			List<TradingHistoryItemVO> historyCL1 = new ArrayList<>();
			List<TradingHistoryItemVO> historyCO1 = new ArrayList<>();
			for (TradingHistoryItemVO item : historyItems) {
				if (StringUtils.equals(item.getSymbol(), TradingConstants.TRADINGECONOMICS_SYMBOL_COM_CLI)) {
					historyCL1.add(item);
				} else {
					historyCO1.add(item);
				}
			}

			////////////////////
			// Transform data //
			////////////////////
			TradingOilPriceSendVO resultItem = new TradingOilPriceSendVO();
			resultItem.setSymbolCL1(symbolCL1);
			resultItem.setSymbolCO1(symbolCO1);
			resultItem.setHistoryCL1(historyCL1);
			resultItem.setHistoryCO1(historyCO1);

			List<TradingOilPriceSendVO> resultList = new ArrayList<>();
			resultList.add(resultItem);

			PageResultVO<TradingOilPriceSendVO> data = new PageResultVO<>();
			data.setElements(resultList);
			data.setCurrentPage(0);
			data.setTotalPage(1);
			data.setTotalElement(resultList.size());

			result.setData(data);
		}

		// Return
		return result;
	}

	/**
	 * Search video
	 *
	 * @param sco
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> searchStockPrice() {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		try {
			// Get data stock America
			TypeTbl zoneAmerica = typeCache.getType(DBConstants.TYPE_CLASS_TRADING_SYMBOL_ZONE,
					DBConstants.TYPE_CODE_TRADING_SYMBOL_ZONE_AMERICA);
			List<TradingSymbolItemVO> resultAmerica = searchStockPriceByZone(zoneAmerica);

			// Get data stock EU
			TypeTbl zoneEU = typeCache.getType(DBConstants.TYPE_CLASS_TRADING_SYMBOL_ZONE,
					DBConstants.TYPE_CODE_TRADING_SYMBOL_ZONE_EU);
			List<TradingSymbolItemVO> resultEU = searchStockPriceByZone(zoneEU);

			// Get data stock Asia
			TypeTbl zoneAsia = typeCache.getType(DBConstants.TYPE_CLASS_TRADING_SYMBOL_ZONE,
					DBConstants.TYPE_CODE_TRADING_SYMBOL_ZONE_ASIA);
			List<TradingSymbolItemVO> resultAsia = searchStockPriceByZone(zoneAsia);

			// Get data stock Africa
			TypeTbl zoneAfrica = typeCache.getType(DBConstants.TYPE_CLASS_TRADING_SYMBOL_ZONE,
					DBConstants.TYPE_CODE_TRADING_SYMBOL_ZONE_AFRICA);
			List<TradingSymbolItemVO> resultAfrica = searchStockPriceByZone(zoneAfrica);

			// Set return data
			TradingStockPriceSendVO resultItemData = new TradingStockPriceSendVO();
			resultItemData.setAmerica(resultAmerica);
			resultItemData.setEurope(resultEU);
			resultItemData.setAsia(resultAsia);
			resultItemData.setAfrica(resultAfrica);

			List<TradingStockPriceSendVO> resultData = new ArrayList<>();
			resultData.add(resultItemData);

			PageResultVO<TradingStockPriceSendVO> data = new PageResultVO<>();
			data.setElements(resultData);
			data.setCurrentPage(0);
			data.setTotalPage(1);
			data.setTotalElement(resultData.size());

			result.setData(data);
		} catch (Exception e) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
		}

		// Return
		return result;
	}

	/**
	 * Get stock price by zone
	 *
	 * @param zone
	 * @return
	 * @throws Exception
	 */
	private List<TradingSymbolItemVO> searchStockPriceByZone(TypeTbl zone) throws Exception {
		// Declare result
		List<TradingSymbolItemVO> result = null;

		// Get all symbol by zone
		TypeTbl stockType = typeCache.getType(DBConstants.TYPE_CLASS_TRADING_SYMBOL_TYPE,
				DBConstants.TYPE_CODE_TRADING_SYMBOL_STOCK);
		SearchNumberVO symbolTypeSearch = new SearchNumberVO();
		symbolTypeSearch.setEq(stockType.getId().doubleValue());

		SearchNumberVO zoneSearch = new SearchNumberVO();
		zoneSearch.setEq(zone.getId().doubleValue());

		TradingSymbolSCO sco = new TradingSymbolSCO();
		sco.setSymbolType(symbolTypeSearch);
		sco.setZone(zoneSearch);
		sco.setDeleteFlag(false);

		Page<TradingSymbolTbl> symbolTbls = tradingSymbolRepository.findAll(sco);

		List<String> symbols = new ArrayList<>();
		if (!CollectionUtils.isEmpty(symbolTbls.getContent())) {
			for (TradingSymbolTbl item : symbolTbls.getContent()) {
				symbols.add(item.getSymbol());
			}
		}

		//////////////
		// Get data //
		//////////////
		if (!CollectionUtils.isEmpty(symbols)) {
			result = new ArrayList<>();

			// Prepare parameter
			Map<String, String> parameters = new HashMap<>();
			parameters.put(TradingConstants.TRADINGECONOMICS_PARAM_KEY, TradingConstants.TRADINGECONOMICS_CONSTANT_KEY);
			parameters.put(TradingConstants.TRADINGECONOMICS_PARAM_OUTPUT_FORMAT,
					TradingConstants.TRADINGECONOMICS_CONSTANT_FORMAT_JSON);

			// Get late time
			int lateTimeSecond = Integer.parseInt(
					configCache.getValueByKey(DataExternalConstants.FINANCE_TRADING_MARKET_STOCK_LATE_TIME_SECOND));

			// Get local file name
			String localFilepath = null;
			switch (zone.getTypeCode()) {
			case DBConstants.TYPE_CODE_TRADING_SYMBOL_ZONE_AMERICA:
				localFilepath = resourcePath
						+ DataExternalConstants.REQUEST_DATA_FILE_PATH_TRADING_MARKET_STOCK_AMERICA;
				break;
			case DBConstants.TYPE_CODE_TRADING_SYMBOL_ZONE_EU:
				localFilepath = resourcePath + DataExternalConstants.REQUEST_DATA_FILE_PATH_TRADING_MARKET_STOCK_EU;
				break;
			case DBConstants.TYPE_CODE_TRADING_SYMBOL_ZONE_ASIA:
				localFilepath = resourcePath + DataExternalConstants.REQUEST_DATA_FILE_PATH_TRADING_MARKET_STOCK_ASIA;
				break;
			case DBConstants.TYPE_CODE_TRADING_SYMBOL_ZONE_AFRICA:
				localFilepath = resourcePath + DataExternalConstants.REQUEST_DATA_FILE_PATH_TRADING_MARKET_STOCK_AFRICA;
				break;
			default:
				break;
			}

			String responseData = tradingWebServiceGet(TradingConstants.TRADINGECONOMICS_API_MARKET_SYMBOL, symbols,
					parameters, lateTimeSecond, localFilepath);
			TradingSymbolItemVO[] response = objectMapperUtil.readValue(responseData,
					new TypeReference<TradingSymbolItemVO[]>() {
					});
			if (response != null) {
				result = Arrays.asList(response);

				////////////////////
				// Transform data //
				////////////////////
				TradingSymbolTbl tradingSymbolTbl;
				for (TradingSymbolItemVO item : result) {
					tradingSymbolTbl = tradingSymbolRepository.findFirstBySymbol(item.getSymbol());
					if (tradingSymbolTbl != null) {
						CountryTbl country = new CountryTbl();
						BeanUtils.copyProperties(tradingSymbolTbl.getCountry(), country);
						item.setCountry(country);
					}
				}
			}
		}

		// Return
		return result;
	}

	/**
	 *
	 * Get method
	 *
	 */
	public String tradingWebServiceGet(String urlApi, List<String> symbols, Map<String, String> parameters,
			int lateTimeSecond, String localFilepath) throws Exception {
		// Declare result
		String result = null;

		// Build URL
		URIBuilder builder = new URIBuilder(TradingConstants.TRADINGECONOMICS_URL);

		// Build symbols
		if (!CollectionUtils.isEmpty(symbols)) {
			String symbolKey = null;
			for (String symbol : symbols) {
				if (symbolKey == null) {
					symbolKey = symbol;
				} else {
					symbolKey = symbolKey + "," + symbol;
				}
			}
			builder.setPath(urlApi + symbolKey);
		} else {
			builder.setPath(urlApi);
		}

		// Build parameters
		if (parameters != null) {
			for (Map.Entry<String, String> param : parameters.entrySet()) {
				builder.setParameter(param.getKey(), param.getValue());
			}
		}

		URI uri = restTemplateService.createURI(builder.toString(), null, parameters);

		// Get data
		if (dataIsOutUpdate(uri.toString(), lateTimeSecond)) {
			// Execute and check status
			result = restTemplateService.getResultCall(uri.toString(), null);

			if (localFilepath != null) {
				FileUtils.updateJsonDataRequest(result, localFilepath);
			}

			// Record external request
			recordRequestExternal(uri.toString(), DBConstants.REQUEST_EXTERNAL_TYPE_DATA,
					this.getClass().getSimpleName() + "." + new Object() {
					}.getClass().getEnclosingMethod().getName());
		} else {
			if (localFilepath != null) {
				result = FileUtils.readLocalFile(localFilepath);
			}
		}

		// Return
		return result;
	}

}
