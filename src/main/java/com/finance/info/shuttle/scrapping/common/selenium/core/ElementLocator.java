package com.finance.info.shuttle.scrapping.common.selenium.core;

import com.finance.info.shuttle.scrapping.common.selenium.enums.LocatorType;
import org.openqa.selenium.By;

public class ElementLocator {

    private final String value;
    private final LocatorType type;

    public ElementLocator(String value, LocatorType type) {
        this.value = value;
        this.type = type;
    }

    public By getBy() {
        switch (type) {
            case ID: return By.id(value);
            case NAME: return By.name(value);
            case CLASS_NAME: return By.className(value);
            case CSS_SELECTOR: return By.cssSelector(value);
            case XPATH: return By.xpath(value);
            case TAG_NAME: return By.tagName(value);
            case LINK_TEXT: return By.linkText(value);
            case PARTIAL_LINK_TEXT: return By.partialLinkText(value);
            default: throw new RuntimeException("Unsupported locator type: " + type);
        }
    }

    public LocatorType getType() {
        return type;
    }

    public static ElementLocator id(String value) {
        return new ElementLocator(value, LocatorType.ID);
    }

    public static ElementLocator name(String value) {
        return new ElementLocator(value, LocatorType.NAME);
    }

    public static ElementLocator className(String value) {
        return new ElementLocator(value, LocatorType.CLASS_NAME);
    }

    public static ElementLocator cssSelector(String value) {
        return new ElementLocator(value, LocatorType.CSS_SELECTOR);
    }

    public static ElementLocator xpath(String value) {
        return new ElementLocator(value, LocatorType.XPATH);
    }

    public static ElementLocator tagName(String value) {
        return new ElementLocator(value, LocatorType.TAG_NAME);
    }

    public static ElementLocator linkText(String value) {
        return new ElementLocator(value, LocatorType.LINK_TEXT);
    }

    public static ElementLocator partialLinkText(String value) {
        return new ElementLocator(value, LocatorType.PARTIAL_LINK_TEXT);
    }

}
