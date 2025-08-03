package com.finance.info.shuttle.scrapping.news.controller;

import com.finance.info.shuttle.scrapping.news.service.NaverNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/naver/news")
@RequiredArgsConstructor
public class NaverNewsController {

    private final NaverNewsService naverNewsService;

    @GetMapping("/finance")
    public String searchNaverFinanceNews() {
        return naverNewsService.searchNaverFinanceNews();
    }
}
