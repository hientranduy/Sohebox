package com.hientran.sohebox.webservice;

import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hientran.sohebox.constants.BittrexConstants;

/**
 * @author hientran
 */
@Service
public class BittrexWebService extends BaseWebService {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("httpClient")
    private CloseableHttpClient httpClient;

    /**
     * 
     * Get method
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
        URIBuilder builder = new URIBuilder(BittrexConstants.BITTREX_URL);
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
