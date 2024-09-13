package com.api.restmusicservice.service;

import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.exceptions.NoContainsKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class LikedMusicService {

    public  ArrayList<MusicDataDto> extractLikedMusic(List<MusicDataDto> musicDataDtoList, HashMap<String, List<Long>> musicsId) {
        String keyForMusics = "likedMusicsId";
        if (!musicsId.containsKey(keyForMusics)) {
            throw new NoContainsKeyException("Ключа " + keyForMusics + " не найдено");
        }
        // Извлекаем список ID понравившейся музыки
        List<Long> musicsIdList = musicsId.get(keyForMusics);

        // Обновляем stateLike для музыки в переданном списке
        setStateLikeToUserMusic(musicDataDtoList, musicsIdList);
        return new ArrayList<>(musicDataDtoList);
    }

    public  void setStateLikeToUserMusic(List<MusicDataDto> musicDataDtoList, List<Long> musicsIdList) {
        for (MusicDataDto musicDataDto : musicDataDtoList) {
            if (musicsIdList.contains(musicDataDto.getSoundid().intValue())) {
                musicDataDto.setStateLike(true); // Устанавливаем лайк
            }
        }
    }
}
