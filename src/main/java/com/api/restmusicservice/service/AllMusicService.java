package com.api.restmusicservice.service;

import com.api.restmusicservice.converterstodto.ConverterMusicData;
import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.exceptions.MusicDataNotFoundException;
import com.api.restmusicservice.repository.MusicDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AllMusicService {
    private final RestTemplateService restTemplateService;
    private final MusicDataRepository musicDataRepository;
    private final ConverterMusicData converterMusicData;
    private final LikedMusicService likedMusicService;


    public List<MusicDataDto> getAllMusicByUserId(List<MusicDataDto> musicDataDtoList, String userToken) {
        ResponseEntity<HashMap> response = restTemplateService.getUserLikedMusics(userToken);

        HashMap<String, List<Long>> musicsId = response.getBody();

        ArrayList<MusicDataDto> musicDataDtos = likedMusicService.extractLikedMusic(musicDataDtoList, musicsId);
        if (musicDataDtos.isEmpty()) {
            throw new MusicDataNotFoundException("Музыка не найдена.");
        }
        return musicDataDtos;
    }

    public List<MusicDataDto> getAllMusic() {
        return musicDataRepository.findAll().
                stream().
                map(converterMusicData::converMusicDataToMusicDataDto).
                toList();
    }
}
