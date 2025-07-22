package com.finance.info.shuttle.scrapping.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SeleniumConfig {

    private final WebDriver webDriver;

    public SeleniumConfig() {
        this.webDriver = new ChromeDriver();
    }
}
