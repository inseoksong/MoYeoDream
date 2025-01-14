package com.project.moyeodream.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Data
@AllArgsConstructor
@Slf4j
public class Criteria {
    private Integer pageNum;        // 현재 페이지
    private Integer amount;         // 한 페이지당 출력되는 데이터 개수
    private String type;            // 검색필터 > 검색기준
    private String keyword;         // 검색필터 > 검색어
    private String category;

    public Criteria(){
        this(1, 10);
    }

    public Criteria(Integer pageNum, Integer amount){
        this.pageNum = pageNum;
        this.amount = amount;
    }

    public String[] getTypes(){
        return type == null? new String[]{} : type.split("");
                            // null 일 경우 빈 문자열 return
    }

    public String[] getCategories(){
        log.info("겟 카데고리 크리테리아 : " + category);
        return category == null? new String[]{} : category.split("");
        // null 일 경우 빈 문자열 return
    }

    public String getListLink() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("pageNum", this.pageNum)
                .queryParam("amount", this.amount)
                .queryParam("type", this.type)
                .queryParam("keyword", this.keyword);

        return builder.toUriString();
    }
}
