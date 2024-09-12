package com.api.restmusicservice.service;

import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.dtos.MusicDataDtoList;
import com.api.restmusicservice.wrappers.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMusicService {
    private final RestTemplate restTemplate;
    private final TrackService trackService;

    @Value("${url-For-Show-Liked-MusicId}")
    String urlForShowLikedMusicId;

    public ResponseEntity<ResponseData> getUserMusicByUserId(List<MusicDataDto> musicDataDtoList, String userToken) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(userToken);  // Добавление заголовка Authorization: Bearer {token}
            // Создание HttpEntity с заголовками
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<HashMap> response = restTemplate.exchange(urlForShowLikedMusicId, HttpMethod.GET, entity, HashMap.class);
            HashMap<String, List<Long>> musicsId = response.getBody();

            // Проверяем, что ответ не пустой и содержит ключ likedMusicsId
            if (musicsId == null || !musicsId.containsKey("likedMusicsId")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Возвращаем 404 если данных нет
            }

            // Извлекаем список ID понравившейся музыки
            List<Long> musicsIdList = musicsId.get("likedMusicsId");

            // Обновляем stateLike для музыки в переданном списке
            for (MusicDataDto musicDataDto : musicDataDtoList) {
                if (musicsIdList.contains(musicDataDto.getSoundid().intValue())) {
                    musicDataDto.setStateLike(true); // Устанавливаем лайк
                }
            }

            // Возвращаем обновленный список
            return ResponseEntity.ok(new MusicDataDtoList(musicDataDtoList));

        } catch (RestClientException e) {
            // Ловим исключения, связанные с RestTemplate
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Возвращаем 500 если произошла ошибка
        }
    }

    public List<ResponseData> getMusicsByMusicsId(List<Long> musicsId) {
        return musicsId.stream()
                .map(trackService::getTrackById).collect(Collectors.toList());
    }
}
