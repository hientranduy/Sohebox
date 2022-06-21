package com.hientran.sohebox.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hientran.sohebox.constants.BittrexConstants;
import com.hientran.sohebox.constants.enums.BittrexPairTblEnum;
import com.hientran.sohebox.constants.enums.TblNameEnum;
import com.hientran.sohebox.entity.BittrexCryptoTbl;
import com.hientran.sohebox.entity.BittrexMarketHistoryTbl;
import com.hientran.sohebox.entity.BittrexMarketSummary24hTbl;
import com.hientran.sohebox.entity.BittrexPairTbl;
import com.hientran.sohebox.exception.ResourceNotFoundException;
import com.hientran.sohebox.repository.BittrexCryptoRepository;
import com.hientran.sohebox.repository.BittrexMarketHistoryRepository;
import com.hientran.sohebox.repository.BittrexMarketSummary24hRepository;
import com.hientran.sohebox.repository.BittrexPairRepository;
import com.hientran.sohebox.sco.BittrexPairSCO;
import com.hientran.sohebox.transformer.BittrexCryptoTransformer;
import com.hientran.sohebox.transformer.BittrexMarketSummary24hTransformer;
import com.hientran.sohebox.transformer.BittrexPairTransformer;
import com.hientran.sohebox.utils.LogUtils;
import com.hientran.sohebox.utils.MyDateUtils;
import com.hientran.sohebox.utils.ObjectMapperUtil;
import com.hientran.sohebox.vo.BittrexCryptoVO;
import com.hientran.sohebox.vo.BittrexMarketHistoryVO;
import com.hientran.sohebox.vo.BittrexMarketSummaryVO;
import com.hientran.sohebox.vo.BittrexOrderBookItemVO;
import com.hientran.sohebox.vo.BittrexOrderBookVO;
import com.hientran.sohebox.vo.BittrexPairVO;
import com.hientran.sohebox.vo.BittrexReponseVO;
import com.hientran.sohebox.webservice.BittrexWebService;

/**
 * @author hientran
 */
@Service
public class BittrexService extends BaseService {

    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BittrexWebService bittrexWebService;

    @Autowired
    private BittrexPairRepository bittrexPairRepository;

    @Autowired
    private BittrexPairTransformer bittrexPairTransformer;

    @Autowired
    private BittrexCryptoRepository bittrexCryptoRepository;

    @Autowired
    private BittrexCryptoTransformer bittrexCryptoTransformer;

    @Autowired
    private BittrexMarketSummary24hRepository bittrexMarketSummary24hRepository;

    @Autowired
    private BittrexMarketSummary24hTransformer bittrexMarketSummary24hTransformer;

    @Autowired
    private BittrexMarketHistoryRepository bittrexMarketHistoryRepository;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    // Key parameters
    private String MARKET = "market";

    private String TYPE = "type";

    /**
     * Check and update pair data
     * 
     * @throws Exception
     *
     */
    public void checkAndUpdatePair() {
        try {
            // Get all current pair
            List<BittrexPairVO> pairs = getAllPairInfo();

            // Process
            if (CollectionUtils.isNotEmpty(pairs)) {
                BittrexPairTbl oldTbl;
                BittrexPairTbl newTbl;
                for (BittrexPairVO vo : pairs) {
                    // Transform
                    newTbl = bittrexPairTransformer.convertToBittrexPairTbl(vo);
                    newTbl.setDeleteFlag(false);
                    newTbl.setExtractDataFlag(false);

                    // Get old record
                    oldTbl = bittrexPairRepository.findByMarketName(vo.getMarketName());
                    if (oldTbl != null) {
                        newTbl.setId(oldTbl.getId());
                        newTbl.setExtractDataFlag(oldTbl.getExtractDataFlag());
                    }

                    // Add or update
                    bittrexPairRepository.save(newTbl);
                }
            }

        } catch (Exception e) {
            LogUtils.writeLogError(e);
        }
    }

    /**
     * 
     * Check and update crypto currency data
     *
     * @throws Exception
     */
    public void checkAndUpdateCrypto() {
        try {
            // Get all current pair
            List<BittrexCryptoVO> currencies = getAllCurrentcyInfo();

            // Process
            if (CollectionUtils.isNotEmpty(currencies)) {
                BittrexCryptoTbl oldTbl;
                BittrexCryptoTbl newTbl;
                for (BittrexCryptoVO vo : currencies) {
                    // Transform
                    newTbl = bittrexCryptoTransformer.convertToBittrexCryptoTbl(vo);
                    newTbl.setDeleteFlag(false);

                    // Get old record
                    oldTbl = bittrexCryptoRepository.findByCurrency(vo.getCurrency());
                    if (oldTbl != null) {
                        newTbl.setId(oldTbl.getId());
                    }

                    // Add or update
                    bittrexCryptoRepository.save(newTbl);
                }
            }
        } catch (Exception e) {
            LogUtils.writeLogError(e);
        }
    }

    /**
     * Update market summary 24h all pair
     * 
     * @throws Exception
     *
     */
    public void checkAndUpdateMarketSummaryAll() {
        try {
            // Get all current pair
            List<BittrexMarketSummaryVO> summaries = getMarketSummary24h();

            // Process
            if (CollectionUtils.isNotEmpty(summaries)) {
                // Exclude pairs that have in schedule selected pair
                List<String> listExcluse = bittrexPairRepository.getAllPairToExtract();
                if (listExcluse == null) {
                    listExcluse = new ArrayList<>();
                }

                BittrexMarketSummary24hTbl summary24hTbl;
                for (BittrexMarketSummaryVO vo : summaries) {
                    if (!listExcluse.contains(vo.getMarketName())) {
                        // Transform
                        summary24hTbl = bittrexMarketSummary24hTransformer.convertToBittrexMarketSummary24hTbl(vo);

                        // Add BittrexMarketSummary24hTbl
                        bittrexMarketSummary24hRepository.save(summary24hTbl);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.writeLogError(e);
        }
    }

    /**
     * Update market summary 24h by selected pairs
     * 
     * @throws Exception
     *
     */
    public void checkAndUpdateMarketSummaryPair() {
        try {
            // Get list selected pairs
            List<String> listPair = bittrexPairRepository.getAllPairToExtract();

            // Process each pair
            if (CollectionUtils.isNotEmpty(listPair)) {
                for (String pair : listPair) {
                    logger.info("Update market summary, pair: " + pair);

                    BittrexMarketSummaryVO vo = getMarketSummary24hByPair(pair);
                    if (vo != null) {
                        // Transform
                        BittrexMarketSummary24hTbl tbl = bittrexMarketSummary24hTransformer
                                .convertToBittrexMarketSummary24hTbl(vo);

                        // Add BittrexMarketSummary24hTbl
                        bittrexMarketSummary24hRepository.save(tbl);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.writeLogError(e);
        }
    }

    /**
     * Update market history
     *
     */
    public void checkAndUpdateMarketHistory() {
        try {
            // Get list selected pairs
            List<String> listPair = bittrexPairRepository.getAllPairToExtract();

            // Process each pair
            if (CollectionUtils.isNotEmpty(listPair)) {
                for (String pair : listPair) {
                    logger.info("Update market history, pair: " + pair);
                    checkAndUpdateMarketHistoryByPair(pair);
                }
            }

        } catch (Exception e) {
            LogUtils.writeLogError(e);
        }
    }

    /**
     * Update market history by pair
     *
     * @param pair
     */
    private void checkAndUpdateMarketHistoryByPair(String pair) throws Exception {
        // Get all current pair
        List<BittrexMarketHistoryVO> histories = getMarketHistoryByPair(pair);

        // Process
        if (CollectionUtils.isNotEmpty(histories)) {
            // Get latest record by pair
            BittrexMarketHistoryTbl latestHistory = bittrexMarketHistoryRepository.findLatestByMarketName(pair);

            // Skip old history
            int listIndex = histories.size() - 1;
            BittrexMarketHistoryVO vo = null;
            if (latestHistory != null) {
                for (int i = listIndex; i >= 0; i--) {
                    vo = histories.get(i);
                    if (vo.getTimeStamp().compareTo(latestHistory.getTimeStamp()) <= 0) {
                        listIndex = listIndex - 1;
                    } else {
                        break;
                    }
                }
            }

            // Record new history
            Date timeStamp = null;
            String orderType = null;
            BigDecimal price = null;
            BigDecimal quantity = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            Integer countOrder = 0;
            if (latestHistory != null) {
                timeStamp = latestHistory.getTimeStamp();
                orderType = latestHistory.getOrderType();
                price = latestHistory.getPrice();
            } else {
                timeStamp = histories.get(histories.size() - 1).getTimeStamp();
                orderType = histories.get(histories.size() - 1).getOrderType();
                price = histories.get(histories.size() - 1).getPrice();
            }

            BittrexMarketHistoryTbl newHistory = null;
            for (int i = listIndex; i >= 0; i--) {
                vo = histories.get(i);

                if (MyDateUtils.compareDateTime(vo.getTimeStamp(), timeStamp)
                        && StringUtils.equals(vo.getOrderType(), orderType)) {
                    quantity = quantity.add(vo.getQuantity());
                    total = total.add(vo.getTotal());
                    countOrder = countOrder + 1;
                } else {
                    // Record data
                    newHistory = new BittrexMarketHistoryTbl();
                    newHistory.setTimeStamp(timeStamp);
                    newHistory.setMarketName(pair);
                    newHistory.setQuantity(quantity);
                    newHistory.setPrice(price);
                    newHistory.setTotal(total);
                    newHistory.setOrderType(orderType);
                    newHistory.setCountOrder(countOrder);
                    if (countOrder > 0) {
                        bittrexMarketHistoryRepository.save(newHistory);
                    }

                    // Save for the next
                    timeStamp = vo.getTimeStamp();
                    orderType = vo.getOrderType();
                    price = vo.getPrice();
                    quantity = vo.getQuantity();
                    total = vo.getTotal();
                    countOrder = 1;
                }
            }

            // Record the last if have
            if (quantity.compareTo(BigDecimal.ZERO) > 0 && countOrder > 0) {
                newHistory = new BittrexMarketHistoryTbl();
                newHistory.setTimeStamp(timeStamp);
                newHistory.setMarketName(pair);
                newHistory.setQuantity(quantity);
                newHistory.setPrice(price);
                newHistory.setTotal(total);
                newHistory.setOrderType(orderType);
                newHistory.setCountOrder(countOrder);
                bittrexMarketHistoryRepository.save(newHistory);
            }
        }
    }

    /**
     * Get all pairs that have extraction flag = true
     *
     * @return
     */
    public List<String> getExtractionPairs() {
        // Declare result
        List<String> result = null;

        try {
            // Get Data
            BittrexPairSCO sco = new BittrexPairSCO();
            sco.setExtractDataFlag(true);
            sco.setDeleteFlag(false);
            Page<BittrexPairTbl> page = bittrexPairRepository.findAll(sco);

            // Transformer
            if (CollectionUtils.isNotEmpty(page.getContent())) {
                result = new ArrayList<>();
                for (BittrexPairTbl tbl : page.getContent()) {
                    result.add(tbl.getMarketName());
                }
            }

        } catch (Exception e) {
            LogUtils.writeLogError(e);
            throw e;
        }

        // Return
        return result;
    }

    /**
     * 
     * Update pairs _ extract flag
     *
     * @param pairs
     * @return
     */
    public boolean updateExtractionPairs(List<BittrexPairTbl> pairs) {
        // Declare result
        boolean result = false;
        try {
            for (BittrexPairTbl pair : pairs) {
                BittrexPairTbl tbl = bittrexPairRepository.findByMarketName(pair.getMarketName());
                if (tbl != null) {
                    tbl.setExtractDataFlag(pair.getExtractDataFlag());
                    bittrexPairRepository.save(tbl);
                } else {
                    throw new ResourceNotFoundException(TblNameEnum.BittrexPairTbl.name(),
                            BittrexPairTblEnum.marketName.name(), pair.getMarketName());
                }
            }

        } catch (Exception e) {
            LogUtils.writeLogError(e);
            throw e;
        }

        // Return
        return result;
    }

    /**
     * 
     * Get market history by pair
     *
     * @param pair
     * @return
     * @throws Exception
     */
    private List<BittrexMarketHistoryVO> getMarketHistoryByPair(String pair) throws Exception {
        // Declare result
        List<BittrexMarketHistoryVO> result = null;

        // Get data
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(MARKET, pair);
        String reponseData = bittrexWebService.get(BittrexConstants.BITTREX_API_GET_MARKET_HISTORY, parameters);

        // Parse data to object
        BittrexReponseVO reponse = objectMapperUtil.readValue(reponseData, BittrexReponseVO.class);

        // Prepare result
        if (reponse.isSuccess()) {
            if (reponse.getResult() != null) {
                result = new ArrayList<>();
                result = objectMapperUtil.readValue(reponse.getResult(),
                        new TypeReference<List<BittrexMarketHistoryVO>>() {
                        });
            }
        }

        // Return
        return result;
    }

    /**
     * 
     * Get order book by pair and type
     *
     * @param pair
     * @param type
     * @return
     * @throws Exception
     */
    public BittrexOrderBookVO getOrderBookByPair(String pair, String type) throws Exception {
        // Declare result
        BittrexOrderBookVO result = null;

        // Get data
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(MARKET, pair);
        parameters.put(TYPE, type.toLowerCase());
        String reponseData = bittrexWebService.get(BittrexConstants.BITTREX_API_GET_ORDER_BOOK, parameters);

        // Parse data to object
        BittrexReponseVO reponse = objectMapperUtil.readValue(reponseData, BittrexReponseVO.class);

        // Prepare result
        if (reponse.isSuccess()) {
            if (reponse.getResult() != null) {
                result = new BittrexOrderBookVO();

                if (StringUtils.equals(type, BittrexConstants.BITTREX_PARAM_TYPE_BOTH)) {
                    result = objectMapperUtil.readValue(reponse.getResult(), new TypeReference<BittrexOrderBookVO>() {
                    });
                } else {
                    List<BittrexOrderBookItemVO> itemList = new ArrayList<>();
                    itemList = objectMapperUtil.readValue(reponse.getResult(),
                            new TypeReference<List<BittrexOrderBookItemVO>>() {
                            });

                    if (StringUtils.equals(type, BittrexConstants.BITTREX_PARAM_TYPE_BUY)) {
                        result.setBuy(itemList);
                    } else {
                        result.setSell(itemList);
                    }
                }
            }
        }

        // Return
        return result;
    }

    /**
     * 
     * Get data summary 24h
     *
     * @return
     * @throws Exception
     */
    public List<BittrexMarketSummaryVO> getMarketSummary24h() throws Exception {
        // Declare result
        List<BittrexMarketSummaryVO> result = null;

        // Get data
        String reponseData = bittrexWebService.get(BittrexConstants.BITTREX_API_GET_MARKET_SUMMARIES, null);

        // Parse data to object
        BittrexReponseVO reponse = objectMapperUtil.readValue(reponseData, BittrexReponseVO.class);

        // Prepare result
        if (reponse.isSuccess()) {
            if (reponse.getResult() != null) {
                result = new ArrayList<>();
                result = objectMapperUtil.readValue(reponse.getResult(),
                        new TypeReference<List<BittrexMarketSummaryVO>>() {
                        });
            }
        }

        // Return
        return result;
    }

    /**
     * 
     * Get data summary 24h by pair
     *
     * @param pair
     * @return
     * @throws Exception
     */
    private BittrexMarketSummaryVO getMarketSummary24hByPair(String pair) throws Exception {
        // Declare result
        BittrexMarketSummaryVO result = null;

        // Get data
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(MARKET, pair);
        String reponseData = bittrexWebService.get(BittrexConstants.BITTREX_API_GET_MARKET_SUMMARY, parameters);

        // Parse data to object
        BittrexReponseVO reponse = objectMapperUtil.readValue(reponseData, BittrexReponseVO.class);

        // Prepare result
        if (reponse.isSuccess()) {
            if (reponse.getResult() != null) {
                List<BittrexMarketSummaryVO> parseData = new ArrayList<>();
                parseData = objectMapperUtil.readValue(reponse.getResult(),
                        new TypeReference<List<BittrexMarketSummaryVO>>() {
                        });
                result = parseData.get(0);
            }
        }

        // Return
        return result;
    }

    /**
     * 
     * Get Bid Ask by pair
     *
     * @param pair
     * @return
     * @throws Exception
     */
    public BittrexMarketSummaryVO getBidAskByPair(String pair) throws Exception {
        // Declare result
        BittrexMarketSummaryVO result = null;

        // Get data
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(MARKET, pair);
        String reponseData = bittrexWebService.get(BittrexConstants.BITTREX_API_GET_TICKER, parameters);

        // Parse data to object
        BittrexReponseVO reponse = objectMapperUtil.readValue(reponseData, BittrexReponseVO.class);

        // Prepare result
        if (reponse.isSuccess()) {
            if (reponse.getResult() != null) {
                result = new BittrexMarketSummaryVO();
                result = objectMapperUtil.readValue(reponse.getResult(), new TypeReference<BittrexMarketSummaryVO>() {
                });
            }
        }

        // Return
        return result;
    }

    /**
     * 
     * Get all currency information
     *
     * @return
     * @throws Exception
     */
    public List<BittrexCryptoVO> getAllCurrentcyInfo() throws Exception {
        // Declare result
        List<BittrexCryptoVO> result = null;

        // Get data
        String reponseData = bittrexWebService.get(BittrexConstants.BITTREX_API_GET_CRYPTO, null);

        // Parse data to object
        BittrexReponseVO reponse = objectMapperUtil.readValue(reponseData, BittrexReponseVO.class);

        // Prepare result
        if (reponse.isSuccess()) {
            if (reponse.getResult() != null) {
                result = new ArrayList<>();
                result = objectMapperUtil.readValue(reponse.getResult(), new TypeReference<List<BittrexCryptoVO>>() {
                });
            }
        }

        // Return
        return result;
    }

    /**
     * 
     * Get all pair information
     *
     * @return
     * @throws Exception
     */
    public List<BittrexPairVO> getAllPairInfo() throws Exception {
        // Declare result
        List<BittrexPairVO> result = null;

        // Get data
        String reponseData = bittrexWebService.get(BittrexConstants.BITTREX_API_GET_PAIR, null);

        // Parse data to object
        BittrexReponseVO reponse = objectMapperUtil.readValue(reponseData, BittrexReponseVO.class);

        // Prepare result
        if (reponse.isSuccess()) {
            if (reponse.getResult() != null) {
                result = new ArrayList<>();
                result = objectMapperUtil.readValue(reponse.getResult(), new TypeReference<List<BittrexPairVO>>() {
                });
            }
        }

        // Return
        return result;
    }
}
