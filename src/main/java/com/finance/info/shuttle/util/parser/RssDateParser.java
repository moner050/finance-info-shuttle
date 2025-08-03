package com.finance.info.shuttle.util.parser;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class RssDateParser {

    private static final SimpleDateFormat rssDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

    public static Date parse(String pubDateStr) {
        try {
            return rssDateFormat.parse(pubDateStr);
        }
        catch (ParseException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
