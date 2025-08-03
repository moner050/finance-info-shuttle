package com.finance.info.shuttle.scrapping.news.controller;

import com.finance.info.shuttle.scrapping.news.service.HankyungNewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/news/hankyung")
@RequiredArgsConstructor
public class HankyungNewsController {

    private final HankyungNewsService hankyungNewsService;

    @GetMapping("/all")
    public String saveAllHankyungNewsData() {
        log.info("HankyungNewsController.saveAllHankyungNewsData");
        hankyungNewsService.saveAllRss();

        return "success";
    }

}
