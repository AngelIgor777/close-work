package com.api.restmusicservice.service;

import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.exceptions.MusicDataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMusicService {
    private final TrackService trackService;
    private final RestTemplateService restTemplateService;
    private final LikedMusicService likedMusicService;

    @Value("${url-For-Show-Liked-MusicId}")
    String urlForShowLikedMusicId;

    public ArrayList<MusicDataDto> getUserMusicListByUserId(List<MusicDataDto> musicDataDtoList, String userToken) {
        ResponseEntity<HashMap> likedMusicsIdList = restTemplateService.getUserLikedMusics(userToken);

        HashMap<String, List<Long>> musicsId = likedMusicsIdList.getBody();

        ArrayList<MusicDataDto> musicDataDtos = likedMusicService.extractLikedMusic(musicDataDtoList, musicsId);
        if (musicDataDtos.isEmpty()) {
            throw new MusicDataNotFoundException("Музыка не найдена.");
        }

        // Возвращаем обновленный список
        return musicDataDtos;
    }


    public void setUserMusicByUserId(MusicDataDto musicDataDto, String userToken) {
        HashMap userLikedMusics = restTemplateService.getUserLikedMusics(userToken).getBody();
        ArrayList<Long> likedMusics = (ArrayList<Long>) likedMusicService.getUserLikedMusics(userLikedMusics);
        if (likedMusics.contains(musicDataDto)) {
            musicDataDto.setStateLike(true);
        }
    }

    public List<MusicDataDto> getMusicsDataByMusicsId(List<Long> musicsId) {
        List<MusicDataDto> musicDataDtoList = musicsId.stream()
                .map(trackService::getTrackById).toList();
        if (musicDataDtoList.isEmpty()) {
            throw new MusicDataNotFoundException("Музыка не найдена");
        }
        return musicDataDtoList;
    }
}
