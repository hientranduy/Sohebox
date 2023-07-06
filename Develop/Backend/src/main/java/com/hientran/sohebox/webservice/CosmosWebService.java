package com.hientran.sohebox.webservice;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CosmosWebService extends BaseWebService {

	private final CloseableHttpClient httpClient;

	/**
	 * 
	 * Get method
	 */
	public String get(URIBuilder builder) throws Exception {
		// Declare result
		String result = null;

		// Build HTTP get
		HttpGet httpGet = createHttpGet(builder);

		// Execute and check status
		CloseableHttpResponse responseBody = httpClient.execute(httpGet);
		result = checkAndGetResult(httpGet, responseBody);

		// Return
		return result;
	}

}
