package com.finance.info.shuttle.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class RestClientUtil {

    private final RestClient restClient;

    public RestClientUtil(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * GET 요청
     * @param url: 요청 보낼 URL
     * @param headers: header 설정
     * @param responseType: return 받을 타입
     * @return T
     * @param <T>: return 타입
     */
    public <T> T get(String url, Map<String, String> headers, Class<T> responseType) {
        RestClient.RequestHeadersSpec<?> requestSpec = restClient.get().uri(url);

        // 헤더가 비어있지 않은 경우에만 헤더 추가
        requestSpec = addHeadersToRequest(requestSpec, headers);

        return requestSpec.retrieve().body(responseType);
    }

    /**
     * POST 요청
     * @param url: 요청 보낼 URL
     * @param headers: header 설정
     * @param body: body 설정
     * @param responseType: return 받을 타입
     * @return T
     * @param <T>: return 타입
     */
    public <T> T post(String url, Map<String, String> headers, Map<String, String> body, Class<T> responseType) {
        // POST 요청 빌더 시작 (contentType은 일반적으로 설정)
        RestClient.RequestBodySpec requestSpec = restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON);

        // 바디가 비어있지 않은 경우에만 바디 추가
        if (body != null && !body.isEmpty()) {
            requestSpec.body(body);
        }

        // 헤더가 비어있지 않은 경우에만 헤더 추가 (반환 타입에 맞게 캐스팅 필요)
        RestClient.RequestHeadersSpec<?> finalRequestSpec = addHeadersToRequest(requestSpec, headers);

        return finalRequestSpec.retrieve().body(responseType);
    }

    public <T> ResponseEntity<T> getWithBuilder(UriComponentsBuilder uriBuilder, Map<String, String> headers, Class<T> responseType) throws RestClientException {
        // 여기서 최종적으로 URL을 빌드하고 인코딩합니다.
        String finalUrl = uriBuilder.build().encode(StandardCharsets.UTF_8).toUriString();

        RestClient.RequestHeadersSpec<?> requestSpec = restClient.get().uri(finalUrl);
        requestSpec = addHeadersToRequest(requestSpec, headers);
        return requestSpec.retrieve().toEntity(responseType);
    }

    /**
     * Header 값 설정 메서드
     * @param requestSpec: url 및 Body 가 담긴
     * @param headers: header 설정
     * @return
     * @param <T>: return 타입
     */
    private <T extends RestClient.RequestHeadersSpec<?>> T addHeadersToRequest(T requestSpec, Map<String, String> headers) {
        // Optional을 사용하여 null 및 빈 맵 검사를 간결하게 처리
        return Optional.ofNullable(headers)
                .filter(map -> !map.isEmpty())
                .map(map -> (T) requestSpec.headers(httpHeaders -> map.forEach(httpHeaders::add)))
                .orElse(requestSpec);
    }

}
