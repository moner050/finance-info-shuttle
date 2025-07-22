package com.finance.info.shuttle.scrapping.common.driver;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;

public class WebDriverProvider {

    private static final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return WEB_DRIVER_THREAD_LOCAL.get();
    }

    /**
     * 크롬 드라이버 가져오기
     * @param headless: 화면에 보여줄지 말지 결정
     * @return WebDriver
     */
    public static void setDriver(boolean headless, Duration implicitWait) {
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
        driver = new ChromeDriver(chromeOptions);

        driver.manage().timeouts().implicitlyWait(implicitWait);
        driver.manage().window().maximize();
        WEB_DRIVER_THREAD_LOCAL.set(driver);
    }


    /**
     * 현재 스레드에 할당된 WebDriver 인스턴스를 종료합니다.
     */
    public static void quitDriver() {
        WebDriver driver = WEB_DRIVER_THREAD_LOCAL.get();
        if (driver != null) {
            driver.quit();
            WEB_DRIVER_THREAD_LOCAL.remove();
        }
    }

}
