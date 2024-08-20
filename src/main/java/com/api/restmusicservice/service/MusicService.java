package com.api.restmusicservice.service;

import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.entity.Genre;
import com.api.restmusicservice.entity.MusicData;
import com.api.restmusicservice.repository.MusicDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicDataRepository musicDataRepository;


    public MusicDataDto getMusicById(Long id) {
        Optional<MusicData> musicById = musicDataRepository.findById(id);
        return converMusicDataToMusicDataDto(musicById.get());
    }

    public List<MusicDataDto> getMusicByGenreName(String genreName) {
        List<MusicDataDto> musicDataDtos = new LinkedList<>();
        List<MusicData> musicDataByGenreName = musicDataRepository.getMusicDataByGenreName(genreName);
        for (MusicData musicData : musicDataByGenreName) {
            musicDataDtos.add(converMusicDataToMusicDataDto(musicData));
        }
        return musicDataDtos;
    }

    private MusicDataDto converMusicDataToMusicDataDto(MusicData musicData) {
        if (musicData.getGenre().getName().isEmpty()) {
            musicData.getGenre().setName("Popular");
        }


        return MusicDataDto.builder().musicUrl(musicData.getMusicUrl())
                .genre(musicData.getGenre().getName())
                .title(musicData.getTitle())
                .artistName(musicData.getArtist().getAuthorName())
                .soundId(musicData.getId())
                .coverSmallURL(musicData.getCover().getCoverSmallUrl())
                .coverMediumURL(musicData.getCover().getCoverMediumUrl())
                .durationSeconds(musicData.getDurationSeconds()).build();
    }
}