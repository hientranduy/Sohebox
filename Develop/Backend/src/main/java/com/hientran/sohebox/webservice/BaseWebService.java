package com.hientran.sohebox.webservice;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hientran.sohebox.cache.TypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.RequestExternalVO;
import com.hientran.sohebox.entity.RequestExternalTbl;
import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.sco.RequestExternalSCO;
import com.hientran.sohebox.sco.SearchDateVO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.service.RequestExternalService;
import com.hientran.sohebox.utils.MyDateUtils;

public class BaseWebService {

	@Value("${rest.template.timeout.read}")
	private int timeOutRead;

	@Value("${rest.template.timeout.connect}")
	private int timeOutConnect;

	@Autowired
	private RequestExternalService requestExternalService;

	@Autowired
	private TypeCache typeCache;

	/**
	 *
	 * Create http GET
	 *
	 * @param builder
	 * @return
	 * @throws URISyntaxException
	 */
	protected HttpGet createHttpGet(URIBuilder builder) throws URISyntaxException {
		// Configured get HTTP request
		RequestConfig.Builder requestConfig = RequestConfig.custom();
		requestConfig.setConnectTimeout(Timeout.ofMilliseconds(Long.valueOf(timeOutConnect)));
		requestConfig.setConnectionRequestTimeout(Timeout.ofMilliseconds(Long.valueOf(timeOutConnect)));

		// Create httpGet
		HttpGet httpGet = new HttpGet(builder.build());
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("Accept-Charset", "utf-8");
		httpGet.setUri(builder.build());
		httpGet.setConfig(requestConfig.build());
		return httpGet;
	}

	/**
	 *
	 * Create http GET
	 *
	 * @param builder
	 * @return
	 * @throws URISyntaxException
	 */
	protected HttpGet createHttpGet(URI uri) throws URISyntaxException {
		// Configured get HTTP request
		RequestConfig.Builder requestConfig = RequestConfig.custom();
		requestConfig.setConnectTimeout(Timeout.ofMilliseconds(Long.valueOf(timeOutConnect)));
		requestConfig.setConnectionRequestTimeout(Timeout.ofMilliseconds(Long.valueOf(timeOutConnect)));

		// Create httpGet
		HttpGet httpGet = new HttpGet(uri);
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("Accept-Charset", "utf-8");
		httpGet.setUri(uri);
		httpGet.setConfig(requestConfig.build());
		return httpGet;
	}

	/**
	 *
	 * Validate HTTP status and get return
	 *
	 * @param method
	 * @param responseBody
	 * @return
	 * @throws WebServiceException
	 * @throws ParseException
	 * @throws IOException
	 */
	protected String checkAndGetResult(HttpGet httpGet, CloseableHttpResponse responseBody)
			throws ParseException, IOException {
		return EntityUtils.toString(responseBody.getEntity());
	}

	/**
	 * Record external request
	 *
	 * @throws Exception
	 *
	 */
	protected void recordRequestExternal(String requestUrl, String requestTypeCode, String note) throws Exception {
		TypeTbl type = new TypeTbl();
		type.setTypeCode(requestTypeCode);

		RequestExternalVO vo = new RequestExternalVO();
		vo.setRequestUrl(requestUrl);
		vo.setNote(note);
		vo.setRequestType(type);
		requestExternalService.create(vo);
	}

	/**
	 * Check if data is out update
	 *
	 * @param string
	 * @return
	 */
	protected boolean dataIsOutUpdate(String url, int lateTimeSecond) {
		// Declare result
		boolean result = true;

		// Search request
		SearchTextVO requestUrl = new SearchTextVO();
		requestUrl.setEq(url);

		TypeTbl requestType = typeCache.getType(DBConstants.TYPE_CLASS_REQUEST_EXTERNAL_TYPE,
				DBConstants.REQUEST_EXTERNAL_TYPE_DATA);
		SearchNumberVO requestTypeId = new SearchNumberVO();
		requestTypeId.setEq(requestType.getId().doubleValue());

		SearchDateVO createdDate = new SearchDateVO();
		createdDate.setGe(MyDateUtils.addMinusSecond(new Date(), lateTimeSecond * -1));

		RequestExternalSCO sco = new RequestExternalSCO();
		sco.setRequestUrl(requestUrl);
		sco.setRequestTypeId(requestTypeId);
		sco.setCreatedDate(createdDate);
		List<RequestExternalTbl> requests = requestExternalService.search(sco);

		if (CollectionUtils.isNotEmpty(requests)) {
			result = false;
		}

		// Return
		return result;
	}
}
