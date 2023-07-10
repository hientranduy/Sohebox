package com.hientran.sohebox.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.cache.ConfigCache;
import com.hientran.sohebox.constants.QuandlConstants;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.QuandlOpecOrbSendVO;
import com.hientran.sohebox.dto.QuandlReponseVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.utils.MyDateUtils;
import com.hientran.sohebox.utils.ObjectMapperUtil;
import com.hientran.sohebox.webservice.QuandlWebService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuandlService extends BaseService {

	private final QuandlWebService quandlWebService;
	private final ObjectMapperUtil objectMapperUtil;
	private final ConfigCache configCache;

	/**
	 * Search video
	 *
	 * @param sco
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> searchWTIOilPrices() {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Calculate start date
		Date startDate = MyDateUtils.addMinusDate(new Date(), -7);

		// Get data
		Map<String, String> parameters = new HashMap<>();
		parameters.put(QuandlConstants.QUANDL_PARAM_KEY, configCache.getValueByKey(QuandlConstants.QUANDL_KEY_API));
		parameters.put(QuandlConstants.QUANDL_PARAM_START_DATE,
				MyDateUtils.formatDate(startDate, MyDateUtils.YYYYMMDD));

		try {
			String responseData = quandlWebService.get(QuandlConstants.QUANDL_API_OPEC_ORB, parameters);

			// Parse data to object
			QuandlReponseVO response = objectMapperUtil.readValue(responseData, QuandlReponseVO.class);

			// Prepare result
			if (response.getDataset() != null && response.getDataset().getData() != null) {
				List<QuandlOpecOrbSendVO> sendList = new ArrayList<>();
				for (Object[] object : response.getDataset().getData()) {
					QuandlOpecOrbSendVO item = new QuandlOpecOrbSendVO();
					item.setDate((String) object[0]);
					item.setPrice((Double) object[1]);

					sendList.add(item);
				}

				// Set return data
				PageResultVO<QuandlOpecOrbSendVO> data = new PageResultVO<>();
				data.setElements(sendList);
				data.setCurrentPage(0);
				data.setTotalPage(1);
				data.setTotalElement(sendList.size());

				// Set data return
				result.setData(data);
			}

		} catch (Exception e) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
		}

		// Return
		return result;
	}

}
