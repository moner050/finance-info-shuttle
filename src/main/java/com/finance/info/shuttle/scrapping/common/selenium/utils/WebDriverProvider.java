package com.finance.info.shuttle.scrapping.common.selenium.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;

@Slf4j
@Component
public class WebDriverProvider {

    private final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    public WebDriver getDriver() {
        return getDriver(true);
    }

    public WebDriver getDriver(boolean headless) {
        WebDriver driver;

        chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        if (headless) {
            chromeOptions.addArguments("--headless=new");
        }
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--allow-running-insecure-content");
        chromeOptions.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(chromeOptions);
        log.info("get Chrome driver complete");

        return driver;
    }

    /**
     * 크롬 드라이버 가져오기
     * @param headless: 화면에 보여줄지 말지 결정
     * @return WebDriver
     * 일단 비활성화
     */
    public void setDriver(boolean headless) {
        WebDriver driver;

        chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        if (headless) {
            chromeOptions.addArguments("--headless=new");
        }
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--allow-running-insecure-content");
        chromeOptions.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(chromeOptions);

        WEB_DRIVER_THREAD_LOCAL.set(driver);

        log.info("Chrome driver loaded");
    }


    /**
     * 현재 스레드에 할당된 WebDriver 인스턴스를 종료
     * 일단 비활성화.
     */
    public void closeDriver() {
        WebDriver driver = WEB_DRIVER_THREAD_LOCAL.get();
        if (driver != null) {
            driver.close();
            WEB_DRIVER_THREAD_LOCAL.remove();

            log.info("Chrome driver close successfully");
        }
        else {
            log.info("Chrome driver not loaded or already closed");
        }
    }

}
