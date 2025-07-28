package com.finance.info.shuttle.scrapping.news.service;

import com.finance.info.shuttle.scrapping.common.constants.UrlConstant;
import com.finance.info.shuttle.util.RestClientUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverNewsService {

    private final RestClientUtil restClientUtil;

    @Value("${naver-client-id}")
    private String clientId;
    @Value("${naver-client-secret}")
    private String clientSecret;

    private Map<String, String> fixedHeaders = new HashMap<>();
    private Map<String, String> naverNewsQueryParams = new HashMap<>();

    @PostConstruct
    public void initParams() {
        fixedHeaders.put("Accept", "*/*");
        fixedHeaders.put("Content-Type", "application/json");
        fixedHeaders.put("X-Naver-Client-Id", clientId);
        fixedHeaders.put("X-Naver-Client-Secret", clientSecret);

        naverNewsQueryParams.put("display", "100");
        naverNewsQueryParams.put("start", "1");
        naverNewsQueryParams.put("sort", "date");
    }

    public String searchNaverFinanceNews() {
        naverNewsQueryParams.put("query", "경제");

        UriComponentsBuilder url = UrlConstant.NAVER_API_BASE_URL.getBuilder(naverNewsQueryParams, "v1","search","news.json");

        log.info("url: {}, params: {}", url, naverNewsQueryParams);

        ResponseEntity<String> response = restClientUtil.getWithBuilder(url, fixedHeaders, String.class);

        log.info("response: {}", response.getBody());

        return response.getBody();
    }
}
