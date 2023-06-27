package com.hientran.sohebox.configuration;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RestTemplateConfig {

	Environment env;
	RestTemplate restTemplate;
	ObjectMapper objectMapper;

	@Autowired
	public RestTemplateConfig(Environment env, RestTemplateBuilder builder, ObjectMapper objectMapper) {
		this.env = env;
		this.restTemplate = builder.build();
		this.objectMapper = objectMapper;
	}

	@Bean("restTemplateL")
	public RestTemplate restTemplateL() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		// Read config timeout
		int readTimeout = env.getProperty("rest.template.timeout.read", Integer.class, 60000);
		int connectTimeout = env.getProperty("rest.template.timeout.connect", Integer.class, 60000);

		// SSL
		final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
		final SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory sSLConnectionSocketFactory = SSLConnectionSocketFactoryBuilder.create()
				.setSslContext(sslContext).setTlsVersions(TLS.V_1_3).build();

		PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
				.setSSLSocketFactory(sSLConnectionSocketFactory)
				.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(Timeout.ofMinutes(readTimeout)).build())
				.setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT).setConnPoolPolicy(PoolReusePolicy.LIFO)
				.setConnectionTimeToLive(TimeValue.ofMinutes(readTimeout)).build();

		CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(StandardCookieSpec.STRICT).build())
				.build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(client);
		requestFactory.setConnectTimeout(connectTimeout);
		requestFactory.setConnectionRequestTimeout(connectTimeout);

		RestTemplate restTemplate = new RestTemplate(requestFactory);

		return restTemplate;
	}
}