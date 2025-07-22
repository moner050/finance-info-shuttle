package com.finance.info.shuttle.scrapping.common.enums;

import org.openqa.selenium.By;

// 요소 찾기 방법
public enum LocatorType {
    ID {
        @Override
        public By getBy(String value) {
            return By.id(value);
        }
    },
    CLASS_NAME {
        @Override
        public By getBy(String value) {
            return By.className(value);
        }
    },
    CSS_SELECTOR {
        @Override
        public By getBy(String value) {
            return By.cssSelector(value);
        }
    },
    XPATH {
        @Override
        public By getBy(String value) {
            return By.xpath(value);
        }
    },
    NAME {
        @Override
        public By getBy(String value) {
            return By.name(value);
        }
    },
    TAG_NAME {
        @Override
        public By getBy(String value) {
            return By.tagName(value);
        }
    },
    LINK_TEXT {
        @Override
        public By getBy(String value) {
            return By.linkText(value);
        }
    },
    PARTIAL_LINK_TEXT {
        @Override
        public By getBy(String value) {
            return By.partialLinkText(value);
        }
    };

    public abstract By getBy(String value);
}
