package com.loginAPI.config.strategy;

import java.util.Collections;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public abstract class SocialLoadStrategy {
	
	ParameterizedTypeReference<Map<String, Object>> RESPONSE_TYPE = new ParameterizedTypeReference<>() {};
	// 제네릭 타입으로 받기
	
	protected final RestTemplate restTemplate = new RestTemplate();
	// http 통신 사용
	
	public String getProviderId(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		
		setHeaders(accessToken, headers);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		
		return sendRequestToSocialSite(request);
	}

	protected abstract String sendRequestToSocialSite(HttpEntity<MultiValueMap<String, String>> request);

	private void setHeaders(String accessToken, HttpHeaders headers) {
		headers.set("Authorization", "Bearer " + accessToken);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	}
	
	
}
