package com.finance.info.shuttle.scrapping.news.analyze;

import com.finance.info.shuttle.scrapping.common.selenium.core.ElementLocator;
import com.finance.info.shuttle.scrapping.common.selenium.core.PageElementConfig;
import com.finance.info.shuttle.scrapping.common.selenium.core.ScrappingSubject;
import com.finance.info.shuttle.scrapping.common.selenium.enums.ElementType;
import com.finance.info.shuttle.scrapping.common.selenium.handler.FormHandler;
import com.finance.info.shuttle.scrapping.common.selenium.scrapping.BaseScrappingProcessor;
import com.finance.info.shuttle.scrapping.common.selenium.utils.WebDriverProvider;
import com.finance.info.shuttle.scrapping.news.entity.NewsInfoEntity;
import com.finance.info.shuttle.scrapping.news.repository.NewsInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class NewsContentDetailScrappingProcessor extends BaseScrappingProcessor {

    public NewsContentDetailScrappingProcessor(WebDriverProvider driverProvider, NewsInfoRepository newsInfoRepository) {
        super(driverProvider, newsInfoRepository);
    }

    @Override
    protected PageElementConfig getPageConfig() {
        return PageElementConfig.builder()
                .addElement("articletxt", ElementLocator.id("articletxt"), ElementType.DIV)
                .build();
    }

    @Override
    public Map<String, String> executeScrapping(WebDriver driver) {
        PageElementConfig pageConfig = getPageConfig();

        try {
            FormHandler handler = new FormHandler(driver, pageConfig);

            // DB 에서 Content Simple 이 비어있는 뉴스 데이터 추출
            NewsInfoRepository newsInfoRepository = getNewsInfoRepository();

            List<NewsInfoEntity> emptyNewsContent = newsInfoRepository.findByContentDetailIsNull();

            log.info("비어있는 뉴스 컨텐츠 개수: " + emptyNewsContent.size());

            for (NewsInfoEntity newsInfoEntity : emptyNewsContent) {
                log.info(newsInfoEntity.getTitle() + " 뉴스 ContentDetail 스크래핑 시작.");
                // 해당 홈페이지 이동
                driver.get(newsInfoEntity.getLink());

                // 페이지 로딩 대기
                handler.waitLoadPage(5);

                // 페이지의 내용 파싱
                String newsDetailText = handler.getElementText("articletxt");

                // 광고 관련 텍스트 삭제
                newsDetailText = newsDetailText.replaceAll("ADVERTISEMENT", "");

                // 페이지 내용 DB 에 UPDATE
                newsInfoEntity.withContentDetail(newsDetailText);
                newsInfoRepository.save(newsInfoEntity);

                log.info(newsInfoEntity.getTitle() + " 뉴스 ContentDetail 스크래핑 완료.");
                log.info(newsDetailText);
            }

        }
        catch (Exception e) {
            log.error(e.getMessage());
        }

        return Map.of();
    }

    @Override
    public boolean isSupported(ScrappingSubject scrappingSubject) {
        return ScrappingSubject.NEWS.equals(scrappingSubject);
    }
}
