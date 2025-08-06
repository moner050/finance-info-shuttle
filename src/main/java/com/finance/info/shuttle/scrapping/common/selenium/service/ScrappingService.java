package com.finance.info.shuttle.scrapping.common.selenium.service;

import com.finance.info.shuttle.scrapping.common.selenium.core.ScrappingSubject;
import com.finance.info.shuttle.scrapping.common.selenium.scrapping.ScrappingProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ScrappingService {

    private final List<ScrappingProcessor> scrappingProcessors;

    public ScrappingService(List<ScrappingProcessor> scrappingProcessors) {
        this.scrappingProcessors = scrappingProcessors;
    }

    public Map<String, String> processScrapping(ScrappingSubject scrappingSubject) {
        log.info("Processing scrapping for subject: {}", scrappingSubject.name());

        ScrappingProcessor scrappingSub = findProcessor(scrappingSubject);
        if (scrappingSub == null) {
            throw new RuntimeException("scrappingSubject not available: " + scrappingSubject);
        }

        return scrappingSub.initScrappingProcessor();
    }

    /**
     * 스크래핑할 주제 가져오기
     * @return ScrappingProcessor
     */
    private ScrappingProcessor findProcessor(ScrappingSubject scrappingSubject) {
        return scrappingProcessors.stream()
                .filter(processor -> processor.isSupported(scrappingSubject))
                .findFirst()
                .orElse(null);
    }
}
