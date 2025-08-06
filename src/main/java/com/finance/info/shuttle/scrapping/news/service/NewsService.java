package com.finance.info.shuttle.scrapping.news.service;

import com.finance.info.shuttle.scrapping.common.selenium.core.ScrappingSubject;
import com.finance.info.shuttle.scrapping.common.selenium.service.ScrappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {

    private final ScrappingService scrappingService;

    public Map<String, String> processNewsDetailTextScrapping() {
        log.info(" ========================= processNewsDetailTextScrapping Start =========================");

        return scrappingService.processScrapping(ScrappingSubject.NEWS);
    }
}
