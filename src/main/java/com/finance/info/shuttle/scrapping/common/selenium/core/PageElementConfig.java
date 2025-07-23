package com.finance.info.shuttle.scrapping.common.selenium.core;

import com.finance.info.shuttle.scrapping.common.selenium.enums.ElementType;
import com.finance.info.shuttle.scrapping.common.selenium.enums.LocatorType;
import org.openqa.selenium.By;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageElementConfig {
    private final Map<String, ElementLocator> locators;
    private final Map<String, ElementType> elementTypes;
    private final boolean hasIframe;
    private final String iframeLocator;
    private final LocatorType iframeLocatorType;
    private final Set<String> selectElementKeys;
    private final Set<String> checkboxElementKeys;

    public PageElementConfig(Builder builder) {
        this.locators = builder.locators;
        this.elementTypes = builder.elementTypes;
        this.hasIframe = builder.hasIframe;
        this.iframeLocator = builder.iframeLocator;
        this.iframeLocatorType = builder.iframeLocatorType;
        this.selectElementKeys = Collections.unmodifiableSet(new HashSet<>(builder.selectElementKeys));
        this.checkboxElementKeys = Collections.unmodifiableSet(new HashSet<>(builder.checkboxElementKeys));
    }

    public ElementLocator getLocator(String key) {
        return locators.get(key);
    }

    public Map<String, ElementLocator> getLocators() {
        return locators;
    }

    public ElementType getElementType(String key) {
        return elementTypes.getOrDefault(key, ElementType.INPUT);
    }

    public boolean hasIframe() {
        return hasIframe;
    }

    public boolean hasElement(String key) {
        return locators.containsKey(key);
    }

    public By getIframeBy() {
        if (!hasIframe) {
            return null;
        }
        switch (iframeLocatorType) {
            case ID:
                return By.id(iframeLocator);
            case NAME:
                return By.name(iframeLocator);
            case CLASS_NAME:
                return By.className(iframeLocator);
            case CSS_SELECTOR:
                return By.cssSelector(iframeLocator);
            case XPATH:
                return By.xpath(iframeLocator);
            default:
                throw new RuntimeException("Unsupported iframe locator type: " + iframeLocatorType);
        }
    }

    public boolean isSelectElement(String key) {
        return selectElementKeys.contains(key);
    }

    public boolean isCheckboxElement(String key) {
        return checkboxElementKeys.contains(key);
    }

    public static Builder builder() {return new Builder();}

    public static class Builder {
        private final Map<String, ElementLocator> locators = new HashMap<>();       /** */
        private final Map<String, ElementType> elementTypes = new HashMap<>();
        private boolean hasIframe = false;
        private String iframeLocator;
        private LocatorType iframeLocatorType;
        private final Set<String> selectElementKeys = new HashSet<>();
        private final Set<String> checkboxElementKeys = new HashSet<>();

        /**
         * key   : 꺼낼때 쓰는 키값
         * value : 페이지 요소의 값
         * type  : 페이지 요소의 종류
         * */
        public Builder addElement(String key, ElementLocator locator, ElementType type) {
            locators.put(key, locator);
            // 만약 SELECT 요소이면 SELECT 키 요소 구분하기 위해 별도로 추가.
            if(type.equals(ElementType.SELECT)) {
                log.info("Add SELECT element key: {}", key);
                selectElementKeys.add(key);
            }
            // 만약 CHECKBOX 요소이면 CHECKBOX 키 요소 구분하기 위해 별도 추가.
            if(type.equals(ElementType.CHECKBOX)) {
                log.info("Add CHECKBOX element key: {}", key);
                checkboxElementKeys.add(key);
            }
            elementTypes.put(key, type);
            return this;
        }

        public Builder setIframe(String iframeLocator, LocatorType iframeLocatorType) {
            this.hasIframe = true;
            this.iframeLocator = iframeLocator;
            this.iframeLocatorType = iframeLocatorType;
            return this;
        }

        public PageElementConfig build() {
            // selectElementKeys, checkboxElementKeys 에 있는 키가 elementLocators 에도 있는지 확인
            for (String selectKey : selectElementKeys) {
                if (!locators.containsKey(selectKey)) {
                    log.warn("Select element key '{}' is not found in elementLocators.", selectKey);
                }
            }
            for(String checkboxKey : checkboxElementKeys) {
                if (!locators.containsKey(checkboxKey)) {
                    log.warn("Checkbox element key '{}' is not found in elementLocators.", checkboxKey);
                }
            }
            return new PageElementConfig(this);
        }

    }
}
