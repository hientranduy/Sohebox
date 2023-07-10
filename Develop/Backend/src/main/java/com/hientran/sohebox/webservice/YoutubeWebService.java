package com.hientran.sohebox.webservice;

import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.GoogleConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class YoutubeWebService extends BaseWebService {

	private final CloseableHttpClient httpClient;

	/**
	 *
	 * Get method
	 *
	 * @param apiName
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public String getLatestVideoByChannel(URIBuilder builder) throws Exception {
		// Declare result
		String result = null;

		// Build HTTP get
		HttpGet httpGet = createHttpGet(builder);

		// Execute and check status
		CloseableHttpResponse responseBody = httpClient.execute(httpGet);
		result = checkAndGetResult(httpGet, responseBody);

		// Record external request
		recordRequestExternal(httpGet.getUri().toString(), DBConstants.REQUEST_EXTERNAL_TYPE_DATA,
				this.getClass().getSimpleName() + "." + new Object() {
				}.getClass().getEnclosingMethod().getName());

		// Return
		return result;
	}

	/**
	 *
	 * Get method: normal case
	 *
	 * @param apiName
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public String get(String apiName, Map<String, String> parameters) throws Exception {
		// Declare result
		String result = null;

		// Build URL
		URIBuilder builder = new URIBuilder(GoogleConstants.GOOGLE_API_URL);
		builder.setPath(apiName);
		if (parameters != null) {
			for (Map.Entry<String, String> param : parameters.entrySet()) {
				builder.setParameter(param.getKey(), param.getValue());
			}
		}

		// Build HTTP get
		HttpGet httpGet = createHttpGet(builder);

		// Execute and check status
		CloseableHttpResponse responseBody = httpClient.execute(httpGet);
		result = checkAndGetResult(httpGet, responseBody);

		// Return
		return result;
	}

}
