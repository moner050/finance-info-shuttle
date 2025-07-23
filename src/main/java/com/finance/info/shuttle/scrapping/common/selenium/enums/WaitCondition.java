package com.finance.info.shuttle.scrapping.common.selenium.enums;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

// 웹 요소 대기 조건
public enum WaitCondition {
    VISIBLE {
        @Override
        public ExpectedCondition<WebElement> getCondition(By locator) {
            return ExpectedConditions.visibilityOfElementLocated(locator);
        }
    },
    CLICKABLE {
        @Override
        public ExpectedCondition<WebElement> getCondition(By locator) {
            return ExpectedConditions.elementToBeClickable(locator);
        }
    },
    PRESENT {
        @Override
        public ExpectedCondition<WebElement> getCondition(By locator) {
            return ExpectedConditions.presenceOfElementLocated(locator);
        }
    },
    INVISIBLE {
        @Override
        public ExpectedCondition<Boolean> getCondition(By locator) {
            return ExpectedConditions.invisibilityOfElementLocated(locator);
        }
    };

    public abstract ExpectedCondition<?> getCondition(By locator);
}
