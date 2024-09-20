package com.api.restmusicservice.service;

import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.exceptions.NoContainsKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class LikedMusicService {

    public ArrayList<MusicDataDto> extractLikedMusic(List<MusicDataDto> musicDataDtoList,
                                                     HashMap<String, List<Long>> musicsId) {
        List<Long> musicsIdList = getUserLikedMusics(musicsId);

        // Обновляем stateLike для музыки в переданном списке
        setStateLikeToUserMusicList(musicDataDtoList, musicsIdList);
        return new ArrayList<>(musicDataDtoList);
    }

    public List<Long> getUserLikedMusics(HashMap<String, List<Long>> musicsId) {
        String keyForMusics = "likedMusicsId";
        if (!musicsId.containsKey(keyForMusics)) {
            throw new NoContainsKeyException("Ключа " + keyForMusics + " не найдено");
        }
        // Извлекаем список ID понравившейся музыки
        List<Long> musicsIdList = musicsId.get(keyForMusics);
        return musicsIdList;
    }

    public void setStateLikeToUserMusicList(List<MusicDataDto> musicDataDtoList, List<Long> musicsIdList) {
        for (MusicDataDto musicDataDto : musicDataDtoList) {
            if (musicsIdList.contains(musicDataDto.getSoundid().intValue())) {
                musicDataDto.setStateLike(true); // Устанавливаем лайк
            }
        }
    }


//    public  void setStateLikeToUserMusic(List<MusicDataDto> musicDataDtoList, List<Long> musicsIdList) {
//        for (MusicDataDto musicDataDto : musicDataDtoList) {
//            if (musicsIdList.contains(musicDataDto.getSoundid().intValue())) {
//                musicDataDto.setStateLike(true); // Устанавливаем лайк
//            }
//        }
//    }
}
