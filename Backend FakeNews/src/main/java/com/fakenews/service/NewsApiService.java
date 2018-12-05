package com.fakenews.service;

import com.fakenews.model.APINewsResult;
import com.fakenews.model.NewsResult;
import com.fakenews.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewsApiService {

    static final int NUMBER_OF_DAYS  = 3;

    @Autowired
    private RestTemplate restTemplate;

    public NewsResult queryNews(String query) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);


        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date startDate = new Date(System.currentTimeMillis() - (NUMBER_OF_DAYS * DAY_IN_MS));
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        String newDateString = sdf.format(startDate);


        String uri = UriComponentsBuilder
                .fromHttpUrl("https://newsapi.org/v2/everything")
                .queryParam("q", query)
                .queryParam("language", "en")
                .queryParam("sortBy", "relevancy")
                .queryParam("apiKey", "4f4e19ab063e47538bacd4ee122f20cf")
                .queryParam("from", newDateString)
                .toUriString();

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<NewsResult> response = restTemplate.exchange(uri, HttpMethod.GET, entity, NewsResult.class);

        if(!response.getStatusCode().equals(HttpStatus.OK)) {
            return null;
        }

        return response.getBody();
    }


    public List<Result> queryNews2(List<String> query) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder uri = UriComponentsBuilder
                .fromHttpUrl("http://eventregistry.org/api/v1/article/getArticles")
                .queryParam("apiKey", "edfe352c-2f1f-4b3d-ade5-6e5924b17854")
                .queryParam("resultType", "articles")
                .queryParam("articlesSortBy", "rel")
                .queryParam("dateStart", LocalDate.now().minusDays(5).toString())
                .queryParam("dataType", "news")
                .queryParam("lang", "eng")
                .queryParam("keywordLoc", "body,title");


        query.forEach(word -> uri.queryParam("keyword", word));

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<APINewsResult> response = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, APINewsResult.class);

        if(!response.getStatusCode().equals(HttpStatus.OK)) {
            return new ArrayList<>();
        }

        if(response.getBody() == null) {
            return new ArrayList<>();
        }

        return response.getBody().getArticles().getResults();
    }
}
