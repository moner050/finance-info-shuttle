package com.finance.info.shuttle.util.parser;

import org.w3c.dom.Document;

import java.util.List;

public abstract class AbstractRssParser<T> {
    public abstract List<T> parse(String rssUrl);

    protected abstract List<T> parseItems(Document doc);

    protected Document loadDocument(String url) throws Exception {
        var factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        var builder = factory.newDocumentBuilder();
        return builder.parse(new java.net.URL(url).openStream());
    }
}
