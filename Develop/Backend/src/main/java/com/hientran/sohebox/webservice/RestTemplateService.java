package com.hientran.sohebox.webservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;

import org.apache.hc.core5.net.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestTemplateService {

	@Autowired
	private RestTemplate restTemplate;

	public String getResultCall(String baseUrl, String pathUrl) throws Exception {
		try {
			// Build URI
			URI uri = createURI(baseUrl, pathUrl, null);

			// Call
			ResponseEntity<String> resultString = restTemplate.exchange(uri, HttpMethod.GET,
					new HttpEntity<>(null, createHeaders()), String.class);
			if (resultString != null && resultString.getBody() != null) {
				return resultString.getBody();
			}
		} catch (Exception e) {
			log.error("ERROR getResultCall String - baseUrl {} - pathUrl {} ::: message {}", baseUrl, pathUrl,
					e.getMessage());
			throw e;
		}

		return null;
	}

	public String getResultCall(String baseUrl, String pathUrl, Object body, Map<String, String> uriVariables)
			throws Exception {
		try {
			// Build URL
			URI uri = createURI(baseUrl, pathUrl, uriVariables);

			// Call
			ResponseEntity<String> resultString = restTemplate.exchange(uri, HttpMethod.GET,
					new HttpEntity<>(body, createHeaders()), String.class);
			if (resultString != null && resultString.getBody() != null) {
				return resultString.getBody();
			}
		} catch (Exception e) {
			log.error("ERROR getResultCall String - baseUrl {} - pathUrl {} - body {} - uriVariables {} ::: message {}",
					baseUrl, pathUrl, body, uriVariables, e.getMessage());
			throw e;
		}

		return null;
	}

	/*
	 * Create request header
	 */
	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	/*
	 * Create URI
	 */
	private URI createURI(String baseUrl, String pathUrl, Map<String, String> uriVariables) throws URISyntaxException {
		URIBuilder builder = new URIBuilder(baseUrl);
		builder.setPath(pathUrl);
		if (uriVariables != null) {
			for (Map.Entry<String, String> param : uriVariables.entrySet()) {
				builder.setParameter(param.getKey(), param.getValue());
			}
		}
		return builder.build();
	}
}
