package com.finance.info.shuttle.scrapping.common.enums;

// 웹 요소 타입
public enum ElementType {
    INPUT("input"),
    BUTTON("button"),
    DIV("div"),
    SPAN("span"),
    A("a"),
    IMG("img"),
    IFRAME("iframe"),
    SELECT("select"),
    OPTION("option"),
    TABLE("table"),
    TR("tr"),
    TD("td"),
    TH("th"),
    UL("ul"),
    LI("li"),
    FORM("form"),
    TEXTAREA("textarea"),
    H1("h1"), H2("h2"), H3("h3"), H4("h4"), H5("h5"), H6("h6"),
    P("p"),
    LABEL("label");

    private final String tagName;

    ElementType(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
