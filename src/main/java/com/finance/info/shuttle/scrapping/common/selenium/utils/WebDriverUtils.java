package com.finance.info.shuttle.scrapping.common.selenium.utils;

import com.finance.info.shuttle.scrapping.common.selenium.core.ElementLocator;
import com.finance.info.shuttle.scrapping.common.selenium.enums.LocatorType;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebDriverUtils {

    /************************************  DIRECT ************************************/
    public static boolean checkElementVisible(WebDriver driver, ElementLocator locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
            return true;
        }
        catch (TimeoutException e) {
            log.warn("Element is not visible: " + locator);
            return false;
        }
    }

    public static WebElement checkElementVisibleAndGetElement(WebDriver driver, ElementLocator locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
    }

    public static boolean checkUrlContains(WebDriver driver, String urlPart) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            wait.until(ExpectedConditions.urlContains(urlPart));
            return true;
        }
        catch (TimeoutException e) {
            log.warn("URL does not contain the expected string: " + urlPart);
            return false;
        }
    }
    /************************************  DIRECT ************************************/

    /************************************  WebDriverWait ************************************/
    // 페이지에 해당 요소가 존재할 때까지 대기
    public static boolean waitForElement(WebDriver driver, ElementLocator locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator.getBy()));
            return true;
        }
        catch (TimeoutException e) {
            log.warn("Element did not become clickable within the timeout: " + locator);
            return false;
        }
    }

    // 페이지에 해당 요소가 클릭 가능해질 때까지 대기
    public static boolean waitForElementClickable(WebDriver driver, ElementLocator locator, int timeoutSecond) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecond));

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator.getBy()));
            wait.until(ExpectedConditions.elementToBeClickable(locator.getBy()));
            return true;
        }
        catch (TimeoutException e) {
            log.warn("Element did not become clickable within the timeout: " + locator);
            return false;
        }
    }

    // 페이지에 해당 요소가 화면에 보일 때까지 대기
    public static boolean waitForElementVisible(WebDriver driver, ElementLocator locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
            return true;
        }
        catch (TimeoutException e) {
            log.warn("Element did not become visible within the timeout: " + locator);
            return false;
        }
    }

    // 페이지에 해당 요소가 화면에 보이지 않을 때까지 대기
    public static void waitForElementInvisible(WebDriver driver, ElementLocator locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator.getBy()));
    }

    // URL 이 바뀔 때까지 대기
    public static boolean waitForUrlChange(WebDriver driver, String originalUrl, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(originalUrl)));
            return true;
        }
        catch (TimeoutException e) {
            log.warn("URL did not change within the timeout: " + originalUrl);
            return false;
        }
    }

    // URL에 해당 문자열이 포함이 될 때까지 대기
    public static boolean waitForUrlContains(WebDriver driver, String urlPart, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            wait.until(ExpectedConditions.urlContains(urlPart));
            return true;
        }
        catch (TimeoutException e) {
            log.warn("URL did not contain the expected string within the timeout: " + urlPart);
            return false;
        }
    }

    // 새 창이 뜰 때까지 대기
    public static void waitForNewWindow(WebDriver driver, int originalWindowCount, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(w -> w.getWindowHandles().size() > originalWindowCount);
    }

    // 새 창이 종료될 때까지 대기
    public static void waitForWindowClose(WebDriver driver, int originalWindowCount, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(w -> w.getWindowHandles().size() < originalWindowCount);
    }

    // 특정 iframe 이 로딩될 때까지 대기
    public static WebDriver waitFrameToBeAvailableAndSwitchToIt(WebDriver driver, int timeoutSeconds, By iframeBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeBy));
    }

    // 페이지가 로딩 될 때 까지 대기
    public static void waitLoadPage(WebDriver driver, int timeoutSecond) {
        WebDriverWait waiter = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecond));

        waiter.until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete")
        );
        log.info("Web control - Payment UI page loaded");
    }

    // 팝업이 생성되었는지 확인
    public static boolean waitVisibilityNewWindow(WebDriver driver, int beforeWindowCount, int timeoutSecond) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecond));

        try {
            wait.until(w -> w.getWindowHandles().size() > beforeWindowCount);
            return true;
        }
        catch (TimeoutException e) {
            log.warn("A new window was not created in time.");
            return false;
        }
    }

    // 창이 하나가 될 때 까지 대기
    public static boolean waitAllNewWindowIsClosed(WebDriver driver, int beforeWindowCount, int timeoutSecond) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecond));

        try {
            wait.until(w -> w.getWindowHandles().size() == beforeWindowCount);
            return true;
        }
        catch (TimeoutException e) {
            log.warn("All new windows were not closed in time.");
            return false;
        }
    }

    // 해당 요소에 targetText 가 나타날때 까지 대기
    public static boolean waitElementTextContains(WebDriver driver, ElementLocator locator, String targetText, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator.getBy(), targetText));
            log.info("Element text contains the expected string: " + targetText);
            return true;
        }
        catch (TimeoutException e) {
            log.warn("Element text did not contain the expected string within the timeout: " + targetText);
            return false;
        }
    }
    /************************************  WebDriverWait ************************************/


    /************************************  WebDriver Switch ************************************/
    // 새롭게 열린 팝업 창이나 탭으로 전환하기
    public static void switchToNewWindow(WebDriver driver) {
        String mainWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();

        for (String handle : windowHandles) {
            if (!handle.equals(mainWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    // iframe 으로 제어권 전환하기
    public static boolean switchToIframe(WebDriver driver, ElementLocator iframeLocator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator.getBy()));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator.getBy()));
            return true;
        }
        catch (TimeoutException e) {
            log.warn("switch to iframe timeout : " + iframeLocator);
            return false;
        }
    }

    // 페이지의 메인 문서(가장 상위 레벨의 HTML 문서)로 다시 전환하기
    public static void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }
    /************************************  WebDriver Switch ************************************/

    /************************************  WebElement ************************************/
    public static void fillInput(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    public static void fillInputUsingJavascript(WebDriver driver, WebElement element, LocatorType locatorType, String value) {
        element.clear();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        switch (locatorType) {
            case ID:
                js.executeScript("document.getElementById('" + element.getAttribute("id") + "').value='"+ value +"';");
                break;
            case NAME:
                js.executeScript("document.getElementsByName('" + element.getAttribute("name") + "')[0].value='"+ value +"';");
                break;
            case CLASS_NAME:
                js.executeScript("document.getElementsByClassName('" + element.getAttribute("class") + "')[0].value='"+ value +"';");
                break;
            case XPATH:
                js.executeScript("document.evaluate('" + element.getAttribute("xpath") + "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.value='"+ value +"';");
                break;
            default:
                fillInput(element, value);
                break;
        }
    }

    // INPUT 태그 초기화
    public static void clearInput(WebElement element) {
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
    }

    // <select> 요소에서 'value' 속성값으로 옵션 선택
    public static void selectByValue(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
    }

    // <select> 요소에서 보이는 텍스트로 옵션 선택
    public static void selectByText(WebElement element, String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    // <select> 요소에서 인덱스(0부터 시작)로 옵션 선택
    public static void selectByIndex(WebElement element, int index) {
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    // 해당 요소 클릭
    public static void clickElement(WebElement element) {
        element.click();
    }

    // 해당 요소(체크박스나 라디오 버튼)가 선택되어 있지 않으면 선택
    public static void checkElement(WebElement element) {
        if(!element.isSelected()) {
            element.click();
        }
    }

    // 해당 요소(체크박스나 라디오 버튼)가 선택되어 있으면 선택 해제
    public static void uncheckElement(WebElement element) {
        if(element.isSelected()) {
            element.click();
        }
    }
    /************************************  WebElement ************************************/
}
