package com.finance.info.shuttle.scrapping.common.constants;

import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

public enum UrlConstant {

    NAVER_API_BASE_URL("https://openapi.naver.com")
    ;

    private final String url;

    UrlConstant(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    // URL 빌더 (쿼리 파라미터 설정, 경로 파라미터 설정)
    public String buildUrl(Map<String, String> queryParams, String... pathParams) {
        UriComponentsBuilder builder = fromHttpUrl(this.url);

        if (pathParams != null && pathParams.length > 0) {
            builder.pathSegment(pathParams);
        }

        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.forEach(builder::queryParam);
        }

        return builder.encode(StandardCharsets.UTF_8).toUriString();
    }

    // 경로 파라미터만 있을 시 사용
    public String buildUrl(String... pathParams) {
        UriComponentsBuilder builder = fromHttpUrl(this.url);

        if (pathParams != null && pathParams.length > 0) {
            builder.pathSegment(pathParams);
        }

        return builder.encode(StandardCharsets.UTF_8).toUriString();
    }

    // 쿼리 파라미터만 있을 시 사용.
    public String buildUrl(Map<String, String> queryParams) {
        UriComponentsBuilder builder = fromHttpUrl(this.url);

        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.forEach(builder::queryParam);
        }

        return builder.encode(StandardCharsets.UTF_8).toUriString();
    }

    // URL 빌더 (쿼리 파라미터 설정, 경로 파라미터 설정)
    public UriComponentsBuilder getBuilder(Map<String, String> queryParams, String... pathParams) {
        UriComponentsBuilder builder = fromHttpUrl(this.url);

        if (pathParams != null && pathParams.length > 0) {
            builder.pathSegment(pathParams);
        }

        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.forEach(builder::queryParam);
        }

        return builder;
    }

    // 경로 파라미터만 있을 시 사용
    public UriComponentsBuilder getBuilder(String... pathParams) {
        UriComponentsBuilder builder = fromHttpUrl(this.url);

        if (pathParams != null && pathParams.length > 0) {
            builder.pathSegment(pathParams);
        }

        return builder;
    }

    // 쿼리 파라미터만 있을 시 사용.
    public UriComponentsBuilder getBuilder(Map<String, String> queryParams) {
        UriComponentsBuilder builder = fromHttpUrl(this.url);

        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.forEach(builder::queryParam);
        }

        return builder;
    }

}
