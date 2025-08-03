package com.finance.info.shuttle.scrapping.news.service;

import com.finance.info.shuttle.scrapping.common.constants.UrlConstant;
import com.finance.info.shuttle.scrapping.news.dto.HankyungRssDto;
import com.finance.info.shuttle.scrapping.news.entity.NewsInfoEntity;
import com.finance.info.shuttle.scrapping.news.repository.NewsInfoRepository;
import com.finance.info.shuttle.util.parser.AbstractRssParser;
import com.finance.info.shuttle.util.parser.RssDateParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HankyungNewsService {

    private final AbstractRssParser<HankyungRssDto> hankyungRssParser;
    private final NewsInfoRepository newsInfoRepository;

    /**
     * 한경 모든 뉴스 데이터 저장
     */
    public void saveAllRss() {
        log.info("한경 모든 뉴스 데이터 저장 시작");
        saveFinanceRss();
        saveEconomyRss();
        saveRealestateRss();
        saveITRss();
        savePoliticsRss();
        saveInternationalRss();
    }

    /**
     * 한경 증권 뉴스 데이터 저장
     */
    public void saveFinanceRss() {
        log.info("한경 증권 뉴스 데이터 저장 시작.");
        String financeUrl = UrlConstant.HANKYUNG_RSS_BASE_URL.buildUrl("finance");
        saveHankyungRss(financeUrl);
    }

    /**
     * 한경 경제 뉴스 데이터 저장
     */
    public void saveEconomyRss() {
        log.info("한경 경제 뉴스 데이터 저장 시작.");
        String financeUrl = UrlConstant.HANKYUNG_RSS_BASE_URL.buildUrl("economy");
        saveHankyungRss(financeUrl);
    }

    /**
     * 한경 부동산 뉴스 데이터 저장
     */
    public void saveRealestateRss() {
        log.info("한경 부동산 뉴스 데이터 저장 시작.");
        String financeUrl = UrlConstant.HANKYUNG_RSS_BASE_URL.buildUrl("realestate");
        saveHankyungRss(financeUrl);
    }

    /**
     * 한경 IT 뉴스 데이터 저장
     */
    public void saveITRss() {
        log.info("한경 IT 뉴스 데이터 저장 시작.");
        String financeUrl = UrlConstant.HANKYUNG_RSS_BASE_URL.buildUrl("it");
        saveHankyungRss(financeUrl);
    }

    /**
     * 한경 정치 뉴스 데이터 저장
     */
    public void savePoliticsRss() {
        log.info("한경 정치 뉴스 데이터 저장 시작.");
        String financeUrl = UrlConstant.HANKYUNG_RSS_BASE_URL.buildUrl("politics");
        saveHankyungRss(financeUrl);
    }

    /**
     * 한경 국제 뉴스 데이터 저장
     */
    public void saveInternationalRss() {
        log.info("한경 국제 뉴스 데이터 저장 시작.");
        String financeUrl = UrlConstant.HANKYUNG_RSS_BASE_URL.buildUrl("international");
        saveHankyungRss(financeUrl);
    }

    private void saveHankyungRss(String link) {
        List<HankyungRssDto> parsedFinanceData = hankyungRssParser.parse(link);

        // 중복되는 데이터는 insert 금지
        for (HankyungRssDto financeRss : parsedFinanceData) {
            if(!newsInfoRepository.existsByLink(financeRss.link())) {
                NewsInfoEntity newsInfoEntity = NewsInfoEntity.builder()
                        .title(financeRss.title())
                        .link(financeRss.link())
                        .pubDate(RssDateParser.parse(financeRss.pubDate()))
                        .build();
                newsInfoRepository.save(newsInfoEntity);
            }
        }
    }

}
