package com.finance.info.shuttle.scrapping.news.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "news_info")
public class NewsInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Comment(value = "뉴스 제목")
    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Comment(value = "뉴스 링크")
    @Column(name = "link", nullable = false, length = 1000)
    private String link;

    @Comment(value = "뉴스 작성일")
    @Column(name = "pub_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pubDate;

    @Comment(value = "뉴스 요약 내용")
    @Column(name = "content_simple", nullable = true, length = 2000)
    private String contentSimple;

    @Comment(value = "뉴스 전체 내용")
    @Column(name = "content_detail", nullable = true, length = 100000)
    private String contentDetail;

    @Comment(value = "뉴스 카테고리")
    @Column(name = "category", nullable = true, length = 200)
    private String category;

    @Comment(value = "DB 생성일")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Builder
    public NewsInfoEntity(String title, String link, Date pubDate) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.createdAt = new Date();
    }

    public void withContentSimple(String contentSimple) {
        this.contentSimple = contentSimple;
    }

    public void withContentDetail(String contentDetail) {
        this.contentDetail = contentDetail;
    }

    public void withCategory(String category) {
        this.category = category;
    }

}
