package com.finance.info.shuttle.scrapping.common.selenium.scrapping;

import com.finance.info.shuttle.scrapping.common.selenium.core.ScrappingSubject;

import java.util.Map;

public interface ScrappingProcessor {
    Map<String, String> initScrappingProcessor();
    boolean isSupported(ScrappingSubject scrappingSubject);
}
