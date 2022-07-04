package com.hientran.sohebox.webservice;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author hientran
 */
@Service
public class CosmosWebService extends BaseWebService {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("httpClient")
    private CloseableHttpClient httpClient;

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
