package com.finance.info.shuttle.scrapping.news.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.info.shuttle.scrapping.news.service.NaverNewsService;

@RestController
@RequestMapping("/api/naver/news")
public class NaverNewsController {

    private final NaverNewsService naverNewsService;

    public NaverNewsController(NaverNewsService naverNewsService) {
        this.naverNewsService = naverNewsService;
    }

    @GetMapping("/finance")
    public String searchNaverFinanceNews() {
        return naverNewsService.searchNaverFinanceNews();
    }
}
