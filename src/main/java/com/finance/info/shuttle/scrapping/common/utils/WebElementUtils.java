package com.finance.info.shuttle.scrapping.common.utils;

import com.finance.info.shuttle.scrapping.common.enums.LocatorType;
import com.finance.info.shuttle.scrapping.common.enums.WaitCondition;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class WebElementUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public WebElementUtils(WebDriver driver, Duration waitTimeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, waitTimeout);
        this.actions = new Actions(driver);
    }

    // 요소 찾기
    public WebElement findElement(LocatorType locatorType, String value) {
        return driver.findElement(locatorType.getBy(value));
    }

    public List<WebElement> findElements(LocatorType locatorType, String value) {
        return driver.findElements(locatorType.getBy(value));
    }

    // 대기 후 요소 찾기
    public WebElement waitForElement(LocatorType locatorType, String value, WaitCondition condition) {
        By locator = locatorType.getBy(value);
        return (WebElement) wait.until(condition.getCondition(locator));
    }

    // 클릭 관련
    public void click(LocatorType locatorType, String value) {
        WebElement element = waitForElement(locatorType, value, WaitCondition.CLICKABLE);
        element.click();
    }

    public void jsClick(LocatorType locatorType, String value) {
        WebElement element = findElement(locatorType, value);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // 텍스트 입력
    public void sendKeys(LocatorType locatorType, String value, String text) {
        WebElement element = waitForElement(locatorType, value, WaitCondition.VISIBLE);
        element.clear();
        element.sendKeys(text);
    }

    // 텍스트 가져오기
    public String getText(LocatorType locatorType, String value) {
        WebElement element = waitForElement(locatorType, value, WaitCondition.VISIBLE);
        return element.getText();
    }

    public String getAttribute(LocatorType locatorType, String value, String attributeName) {
        WebElement element = findElement(locatorType, value);
        return element.getAttribute(attributeName);
    }

    // Select 드롭다운 처리
    public void selectByText(LocatorType locatorType, String value, String text) {
        WebElement element = findElement(locatorType, value);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    public void selectByValue(LocatorType locatorType, String value, String optionValue) {
        WebElement element = findElement(locatorType, value);
        Select select = new Select(element);
        select.selectByValue(optionValue);
    }

    // iframe 처리
    public void switchToFrame(LocatorType locatorType, String value) {
        WebElement frame = findElement(locatorType, value);
        driver.switchTo().frame(frame);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // 윈도우 처리
    public void switchToNewWindow() {
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            driver.switchTo().window(window);
        }
    }

    // URL 관련
    public void navigateToUrl(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isUrlContains(String expectedText) {
        return getCurrentUrl().contains(expectedText);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // 스크롤
    public void scrollToElement(LocatorType locatorType, String value) {
        WebElement element = findElement(locatorType, value);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    // 요소 상태 확인
    public boolean isElementPresent(LocatorType locatorType, String value) {
        try {
            driver.findElement(locatorType.getBy(value));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementVisible(LocatorType locatorType, String value) {
        try {
            WebElement element = driver.findElement(locatorType.getBy(value));
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Actions 관련
    public void hoverOver(LocatorType locatorType, String value) {
        WebElement element = findElement(locatorType, value);
        actions.moveToElement(element).perform();
    }

    public void dragAndDrop(LocatorType sourceLocatorType, String sourceValue,
                            LocatorType targetLocatorType, String targetValue) {
        WebElement source = findElement(sourceLocatorType, sourceValue);
        WebElement target = findElement(targetLocatorType, targetValue);
        actions.dragAndDrop(source, target).perform();
    }
}
