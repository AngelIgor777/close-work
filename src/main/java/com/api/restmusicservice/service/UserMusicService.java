package com.api.restmusicservice.service;

import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.dtos.MusicDataDtoList;
import com.api.restmusicservice.wrappers.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<ResponseData> getUserMusicByUserId(Long id, List<MusicDataDto> musicDataDtoList) {
        System.out.println(musicDataDtoList);
        try {
            // Формируем URL для получения списка понравившейся музыки по ID пользователя
            String URL = "http://localhost:8080/api/v2/showLikedMusic/" + id;

            // Делаем запрос к User сервису для получения списка ID понравившейся музыки
            HashMap<String, List<Long>> musicsId = restTemplate.getForObject(URL, HashMap.class);

            // Проверяем, что ответ не пустой и содержит ключ likedMusicsId
            if (musicsId == null || !musicsId.containsKey("likedMusicsId")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Возвращаем 404 если данных нет
            }

            // Извлекаем список ID понравившейся музыки
            List<Long> musicsIdList = musicsId.get("likedMusicsId");
            System.out.println(musicsIdList);

            // Обновляем stateLike для музыки в переданном списке
            for (MusicDataDto musicDataDto : musicDataDtoList) {
                if (musicsIdList.contains(musicDataDto.getSoundid().intValue())) {
                    System.out.println(musicDataDto);
                    musicDataDto.setStateLike(true); // Устанавливаем лайк
                }
            }

            System.out.println("after");
            System.out.println(musicDataDtoList);
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
