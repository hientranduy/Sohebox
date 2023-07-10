package com.hientran.sohebox.webservice;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.TradingConstants;
import com.hientran.sohebox.utils.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradingWebService extends BaseWebService {

	private final CloseableHttpClient httpClient;

	/**
	 *
	 * Get method
	 *
	 */
	public String get(String urlApi, List<String> symbols, Map<String, String> parameters, int lateTimeSecond,
			String localFilepath) throws Exception {
		// Declare result
		String result = null;

		// Build URL
		URIBuilder builder = new URIBuilder(TradingConstants.TRADINGECONOMICS_URL);

		// Build symbols
		if (CollectionUtils.isNotEmpty(symbols)) {
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

		// Build HTTP get
		HttpGet httpGet = createHttpGet(builder);

		// Get data
		if (dataIsOutUpdate(httpGet.getUri().toString(), lateTimeSecond)) {
			// Execute and check status
			CloseableHttpResponse responseBody = httpClient.execute(httpGet);
			result = checkAndGetResult(httpGet, responseBody);

			if (localFilepath != null) {
				FileUtils.updateJsonDataRequest(result, localFilepath);
			}

			// Record external request
			recordRequestExternal(httpGet.getUri().toString(), DBConstants.REQUEST_EXTERNAL_TYPE_DATA,
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
