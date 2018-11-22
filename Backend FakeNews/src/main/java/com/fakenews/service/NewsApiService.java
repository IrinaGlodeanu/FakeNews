package com.fakenews.service;

import com.fakenews.model.NewsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NewsApiService {

    @Autowired
    private RestTemplate restTemplate;

    public NewsResult queryNews(String query) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        String uri = UriComponentsBuilder
                .fromHttpUrl("https://newsapi.org/v2/everything")
                .queryParam("q", query)
                .queryParam("sortBy", "relevancy")
                .queryParam("apiKey", "4f4e19ab063e47538bacd4ee122f20cf")
                .toUriString();

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<NewsResult> response = restTemplate.exchange(uri, HttpMethod.GET, entity, NewsResult.class);

        if(!response.getStatusCode().equals(HttpStatus.OK)) {
            return null;
        }

        return response.getBody();
    }
}
