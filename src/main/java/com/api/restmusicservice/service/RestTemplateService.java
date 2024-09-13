package com.api.restmusicservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class RestTemplateService {
    private final RestTemplate restTemplate;

    @Value("${url-For-Show-Liked-MusicId}")
    String urlForShowLikedMusicId;

    public ResponseEntity<HashMap> getHashMapLikedMusicResponseEntity(String userToken) {
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(userToken);  // Добавление заголовка Authorization: Bearer {token}
        // Создание HttpEntity с заголовками
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<HashMap> response = restTemplate.exchange(urlForShowLikedMusicId, HttpMethod.GET, entity, HashMap.class);
        return response;
    }
}
