package com.finance.info.shuttle.scrapping.config;

import com.finance.info.shuttle.scrapping.common.selenium.driver.WebDriverProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SeleniumConfig {

    private final WebDriverProvider webDriverProvider;

    public SeleniumConfig(WebDriverProvider webDriverProvider) {
        this.webDriverProvider = webDriverProvider;
    }
}
