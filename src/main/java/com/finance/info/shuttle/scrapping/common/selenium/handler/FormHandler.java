package com.finance.info.shuttle.scrapping.common.selenium.handler;

import com.finance.info.shuttle.scrapping.common.selenium.core.ElementLocator;
import com.finance.info.shuttle.scrapping.common.selenium.core.PageElementConfig;
import com.finance.info.shuttle.scrapping.common.selenium.enums.ElementType;
import com.finance.info.shuttle.scrapping.common.selenium.enums.LocatorType;
import com.finance.info.shuttle.scrapping.common.selenium.utils.WebDriverUtils;
import org.openqa.selenium.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormHandler {

    private final WebDriver driver;
    private final PageElementConfig pageConfig;
    private static final int TIMEOUT_SECOND = 10;

    public FormHandler(WebDriver driver, PageElementConfig pageConfig) {
        this.driver = driver;
        this.pageConfig = pageConfig;
    }

    /**
     * URL 이동해주는 메서드
     * @param url: 접속할 url 주소
     */
    public void navigateToPage(String url) {
        driver.get(url);
    }

    /**
     * iframe 으로 전환
     */
    public boolean switchToIframe(String elementKey, int timeoutSecond) {
        // iframe으로 전환
        ElementLocator prepareLocator = pageConfig.getLocator(elementKey);
        if (prepareLocator != null) {
            return WebDriverUtils.switchToIframe(driver, prepareLocator, timeoutSecond);
        }
        return false;
    }

    /**
     * 원래 화면으로 전환하기
     */
    public void switchToDefaultContent() {
        WebDriverUtils.switchToDefaultContent(driver);
    }

    /**
     * 페이지 로딩 대기
     */
    public void waitLoadPage(int timeoutSecond) {
        WebDriverUtils.waitLoadPage(driver, timeoutSecond);
    }

    /**
     * paymentUrl 에 해당 url 이 포함 될 때 까지 대기
     */
    public boolean waitLoadPageByUrlContains(String paymentUrl, int timeoutSecond) {
        return WebDriverUtils.waitForUrlContains(driver, paymentUrl, timeoutSecond);
    }

    /**
     * 클릭이 가능 해질때 까지 대기
     */
    public boolean waitClickableElement(String elementKey, int timeoutSecond) {
        ElementLocator locator = pageConfig.getLocator(elementKey);
        return WebDriverUtils.waitForElementClickable(driver, locator, timeoutSecond);
    }

    /**
     * 팝업이 생성 될 때 까지 대기
     */
    public boolean waitVisibilityNewWindow(int beforeWindowCount, int timeoutSecond) {
        return WebDriverUtils.waitVisibilityNewWindow(driver, beforeWindowCount, timeoutSecond);
    }

    /**
     * 팝업이 다 꺼질때 까지 대기
     */
    public boolean waitAllNewWindowIsClosed(int beforeWindowCount, int timeoutSecond) {
        return WebDriverUtils.waitAllNewWindowIsClosed(driver, beforeWindowCount, timeoutSecond);
    }

    /**
     * 해당 요소가 보일 때 까지 대기
     */
    public boolean waitForElementVisible(String elementKey, int timeoutSecond) {
        ElementLocator locator = pageConfig.getLocator(elementKey);
        return WebDriverUtils.waitForElementVisible(driver, locator, timeoutSecond);
    }

    /**
     * 해당 요소에 해당 텍스트가 있을 때 까지 대기
     */
    public boolean waitForElementContainsTargetText(String elementKey, String targetText, int timeoutSecond) {
        ElementLocator locator = pageConfig.getLocator(elementKey);
        return WebDriverUtils.waitElementTextContains(driver, locator, targetText, timeoutSecond);
    }

    /**
     * 요소가 보이는지 체크
     */
    public boolean checkElementVisible(String elementKey) {
        ElementLocator locator = pageConfig.getLocator(elementKey);
        return WebDriverUtils.checkElementVisible(driver, locator);
    }

    /**
     * 해당 요소 가져오기
     */
    public WebElement checkElementVisibleAndGetElement(String elementKey) {
        ElementLocator locator = pageConfig.getLocator(elementKey);
        return WebDriverUtils.checkElementVisibleAndGetElement(driver, locator);
    }


    /**
     * URL 에 해당 문자가 포함 되어있는지 확인
     */
    public boolean checkUrlContainsString(String checkString) {
        return WebDriverUtils.checkUrlContains(driver, checkString);
    }

    /**
     * 폼에 데이터들 넣어주는 메서드
     * @param requestBody
     */
    public void fillPaymentForm(Object requestBody) {
        pageConfig.getLocators().forEach((elementKey, locator) -> {
            // DTO 에서 값 추출
            Object fieldValue = extractFieldValue(requestBody, elementKey);
            if(fieldValue == null) {
                log.warn("Field value is null. key: {}", elementKey);
                return;
            }

            // Select 와 sendKeys 모두 문자열 필요
            String valueAsString = fieldValue.toString();

            // 웹 요소 찾기 및 값 입력
            fillElement(elementKey, valueAsString);
        });
    }

    public void fillPaymentFormUsingJavascript(Object requestBody) {
        pageConfig.getLocators().forEach((elementKey, locator) -> {
            // DTO 에서 값 추출
            Object fieldValue = extractFieldValue(requestBody, elementKey);
            if(fieldValue == null) {
                log.warn("Field value is null. key: {}", elementKey);
                return;
            }

            // Select 와 sendKeys 모두 문자열 필요
            String valueAsString = fieldValue.toString();

            // 웹 요소 찾기 및 값 입력
            fillElementUsingJavascript(elementKey, valueAsString);
        });
    }

    public void fillElement(String elementKey, String value) {
        if (!pageConfig.hasElement(elementKey)) {
            throw new RuntimeException("Element not configured: " + elementKey);
        }

        ElementLocator locator = pageConfig.getLocator(elementKey);
        ElementType type = pageConfig.getElementType(elementKey);

        try {
            WebElement element = driver.findElement(locator.getBy());

            switch (type) {
                case INPUT:
                    WebDriverUtils.fillInput(element, value);
                    break;
                case SELECT:
                    WebDriverUtils.selectByValue(element, value);
                    break;
                case CHECKBOX:
                case BUTTON:
                case RADIO:
                case LABEL:
                    WebDriverUtils.checkElement(element);
                    break;
                case DIV:
                case IFRAME:
                case FORM:
                    break;
                default:
                    throw new RuntimeException("Unsupported element type for filling: " + type);
            }

            log.debug("Filled element '{}' with value '{}'", elementKey, value);

        } catch (NoSuchElementException e) {
            throw new RuntimeException("Element not found: " + elementKey);
        }
    }

    public void fillElementUsingJavascript(String elementKey, String value) {
        if (!pageConfig.hasElement(elementKey)) {
            throw new RuntimeException("Element not configured: " + elementKey);
        }

        ElementLocator locator = pageConfig.getLocator(elementKey);
        ElementType type = pageConfig.getElementType(elementKey);

        try {
            WebElement element = driver.findElement(locator.getBy());
            LocatorType locatorType = locator.getType();

            switch (type) {
                case INPUT:
                    WebDriverUtils.fillInputUsingJavascript(driver, element, locatorType, value);
                    break;
                case SELECT:
                    WebDriverUtils.selectByValue(element, value);
                    break;
                case CHECKBOX:
                case BUTTON:
                case RADIO:
                case LABEL:
                    WebDriverUtils.checkElement(element);
                    break;
                case DIV:
                case IFRAME:
                case FORM:
                    break;
                default:
                    throw new RuntimeException("Unsupported element type for filling: " + type);
            }

            log.debug("Filled element '{}' with value '{}'", elementKey, value);

        } catch (NoSuchElementException e) {
            throw new RuntimeException("Element not found: " + elementKey);
        }
    }

    /**
     * 입력 폼 전체 비우는 메서드
     */
    public void clearFormFields() {
        log.info("selenium - clear form fields start");
        // 필드 매핑 및 페이지 설정에 따라 필드 비우기
        for (String elementKey : pageConfig.getLocators().keySet()) {
            try {
                clearElement(elementKey);
            }
            catch (NoSuchElementException nse) {
                log.error("Element not found for key: " + elementKey +
                        " using locator: " + pageConfig.getLocator(elementKey).getBy());
            }
        }
    }

    /**
     * 폼 비우기
     */
    public void clearElement(String elementKey) {
        if (!pageConfig.hasElement(elementKey)) {
            return;
        }

        ElementLocator locator = pageConfig.getLocator(elementKey);
        ElementType type = pageConfig.getElementType(elementKey);

        try {
            WebElement element = driver.findElement(locator.getBy());

            switch (type) {
                case INPUT:
                    WebDriverUtils.clearInput(element);
                    break;
                case SELECT:
                    // Select는 첫 번째 옵션으로 리셋
                    WebDriverUtils.selectByValue(element, "");
                    break;
                case CHECKBOX:
                case RADIO:
                    WebDriverUtils.uncheckElement(element);
                    break;
                case LABEL:
                    WebDriverUtils.checkElement(element);
                    break;
                case BUTTON:
                case DIV:
                case IFRAME:
                case FORM:
                    break;
            }
        } catch (NoSuchElementException e) {
            log.warn("Element not found for clearing: {}", elementKey);
        }
    }

    public void clickElement(String elementKey) {
        if (!pageConfig.hasElement(elementKey)) {
            throw new RuntimeException("Element not configured: " + elementKey);
        }

        ElementLocator locator = pageConfig.getLocator(elementKey);
        WebDriverUtils.waitForElementClickable(driver, locator, TIMEOUT_SECOND);

        WebElement element = driver.findElement(locator.getBy());
        element.click();

        log.info("Clicked element: {}", elementKey);
    }

    public void clickElementUsingJS(String elementKey) {
        if (!pageConfig.hasElement(elementKey)) {
            throw new RuntimeException("Element not configured: " + elementKey);
        }

        ElementLocator locator = pageConfig.getLocator(elementKey);
        WebDriverUtils.waitForElementClickable(driver, locator, TIMEOUT_SECOND);

        WebElement element = driver.findElement(locator.getBy());

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);

        log.info("Clicked element: {}", elementKey);
    }

    public Object extractFieldValue(Object obj, String fieldName) {
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).get(fieldName);
        }

        try {
            String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method getter = obj.getClass().getMethod(getterName);
            return getter.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract field value: " + fieldName);
        }
    }

    /**
     * URL 파라미터 파싱
     * @param url
     * @return Map<String, Object>
     */
    public Map<String, Object> parseUrlParameters(String url) {
        Map<String, Object> result = new HashMap<>();

        String[] urlParts = url.split("\\?");
        if (urlParts.length > 1) {
            String query = urlParts[1];
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length > 1) {
                    result.put(keyValue[0], keyValue[1]);
                }
            }
        } else {
            // 경로 파라미터에서 마지막 값 추출
            try {
                URL tempUrl = new URL(url);
                String path = tempUrl.getPath();
                if (path.endsWith("/")) {
                    path = path.substring(0, path.length() - 1);
                }
                int lastSlash = path.lastIndexOf("/");
                if (lastSlash != -1) {
                    result.put("result", path.substring(lastSlash + 1));
                }
            } catch (MalformedURLException e) {
                log.error("Invalid URL: {}", url);
            }
        }

        return result;
    }


    /**
     * url 주소 파싱
     * @param paramObj
     * @param parameterName
     * @return String
     */
    public String getParameterToString(Object paramObj, String parameterName) {
        if (paramObj instanceof Map) {
            Map<String, String> resMap = (Map<String, String>) paramObj;
            return resMap.get(parameterName);
        }
        else if (paramObj instanceof String) {
            return (String) paramObj;
        }

        return String.valueOf(paramObj);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

}
