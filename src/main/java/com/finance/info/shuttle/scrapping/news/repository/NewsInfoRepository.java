package com.finance.info.shuttle.scrapping.news.repository;

import com.finance.info.shuttle.scrapping.news.entity.NewsInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsInfoRepository extends JpaRepository<NewsInfoEntity, Integer> {
    boolean existsByLink(String link);

    // 요약 내용이 Null 값이거나 비어있는 데이터 가져오기
    List<NewsInfoEntity> findByContentSimpleIsNull();

    // 전체 내용이 Null 값이거나 비어있는 데이터 가져오기
    List<NewsInfoEntity> findByContentDetailIsNull();
}
