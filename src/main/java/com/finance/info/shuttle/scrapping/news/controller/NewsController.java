package com.finance.info.shuttle.scrapping.news.controller;

import com.finance.info.shuttle.scrapping.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/get-detail")
    public String getDetail() {
        newsService.processNewsDetailTextScrapping();

        return "success";
    }
}
