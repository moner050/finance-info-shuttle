package com.finance.info.shuttle.scrapping.news.analyze;

import com.finance.info.shuttle.scrapping.common.selenium.core.ElementLocator;
import com.finance.info.shuttle.scrapping.common.selenium.core.PageElementConfig;
import com.finance.info.shuttle.scrapping.common.selenium.core.ScrappingSubject;
import com.finance.info.shuttle.scrapping.common.selenium.enums.ElementType;
import com.finance.info.shuttle.scrapping.common.selenium.handler.FormHandler;
import com.finance.info.shuttle.scrapping.common.selenium.scrapping.BaseScrappingProcessor;
import com.finance.info.shuttle.scrapping.common.selenium.utils.WebDriverProvider;
import com.finance.info.shuttle.scrapping.news.repository.NewsInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class NewsContentSimpleProcessor extends BaseScrappingProcessor {

    private final String CHATGPT_PROMPT_TEXT = "";

    public NewsContentSimpleProcessor(WebDriverProvider driverProvider, NewsInfoRepository newsInfoRepository) {
        super(driverProvider, newsInfoRepository);
    }

    @Override
    protected PageElementConfig getPageConfig() {
        return PageElementConfig.builder()
                .addElement("articletxt", ElementLocator.id("articletxt"), ElementType.DIV)
                .build();
    }

    @Override
    public Map<String, String> executeScrapping(WebDriver driver) {
        PageElementConfig pageConfig = getPageConfig();

        try {
            FormHandler handler = new FormHandler(driver, pageConfig);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }

        return Map.of();
    }

    @Override
    public boolean isSupported(ScrappingSubject scrappingSubject) {
        return ScrappingSubject.CHATGPT_NEWS_SIMPLE.equals(scrappingSubject);
    }
}
