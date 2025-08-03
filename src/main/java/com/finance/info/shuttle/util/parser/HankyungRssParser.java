package com.finance.info.shuttle.util.parser;

import com.finance.info.shuttle.scrapping.news.dto.HankyungRssDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component("hankyungRssParser")
public class HankyungRssParser extends AbstractRssParser<HankyungRssDto> {

    @Override
    public List<HankyungRssDto> parse(String rssUrl) {
        try {
            Document doc = loadDocument(rssUrl);
            return parseItems(doc);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return List.of();
        }
    }

    @Override
    protected List<HankyungRssDto> parseItems(Document doc) {
        List<HankyungRssDto> itemList = new ArrayList<>();
        NodeList nodes = doc.getElementsByTagName("item");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element item = (Element) nodes.item(i);
            String title = getTagValue("title", item);
            String link = getTagValue("link", item);
            String pubDate = getTagValue("pubDate", item);
            itemList.add(new HankyungRssDto(title, link, pubDate));
        }

        return itemList;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nList = element.getElementsByTagName(tag);
        if (nList.getLength() == 0) return "";
        Node node = nList.item(0);
        if (node == null || node.getFirstChild() == null) return "";
        return node.getFirstChild().getNodeValue().trim();
    }
}
