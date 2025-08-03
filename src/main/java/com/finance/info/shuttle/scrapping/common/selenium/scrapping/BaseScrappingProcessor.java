package com.finance.info.shuttle.scrapping.common.selenium.scrapping;

import com.finance.info.shuttle.scrapping.common.selenium.core.PageElementConfig;
import com.finance.info.shuttle.scrapping.common.selenium.utils.WebDriverProvider;
import com.finance.info.shuttle.scrapping.news.repository.NewsInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.util.Map;

@Slf4j
public abstract class BaseScrappingProcessor implements ScrappingProcessor {

    private final WebDriverProvider driverProvider;
    private final NewsInfoRepository newsInfoRepository;

    public BaseScrappingProcessor(final WebDriverProvider driverProvider, final NewsInfoRepository newsInfoRepository) {
        this.driverProvider = driverProvider;
        this.newsInfoRepository = newsInfoRepository;
    }

    protected NewsInfoRepository getNewsInfoRepository() {
        return newsInfoRepository;
    }

    /**
     * 페이지 설정 가져오기
     */
    protected abstract PageElementConfig getPageConfig();


    /**
     * 각종 스크래핑 로직들이 실제로 호출하는 로직
     * @param driver
     * @return
     */
    public abstract Map<String,String> executeScrapping(final WebDriver driver);

    /**
     * 스크래핑 전 초기 세팅
     * @return
     */
    @Override
    public Map<String, String> initScrappingProcessor() {
        log.info("Initializing scrapping processor Start");

        WebDriver driver = null;

        try {
            driver = driverProvider.getDriver(false);

            if(driver == null) {
                throw new RuntimeException("driver could not be found");
            }

            return executeScrapping(driver);
        }
        catch(Exception e) {
            log.error("scrapping processor failed", e);
            throw new RuntimeException("scrapping processor failed", e);
        }
        finally {
            if(driver != null) {
                driver.close();
                log.info("driver close successfully");
            }
        }
    }
}
