package com.api.restmusicservice.service;

import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.entity.MusicData;
import com.api.restmusicservice.exceptions.MusicDataNotFoundException;
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


    public MusicDataDto getMusicById(Long id) throws MusicDataNotFoundException {
        Optional<MusicData> musicById = musicDataRepository.findById(id);
        if (musicById.isEmpty()) {
            throw new MusicDataNotFoundException("Cannot return music by id '" + id + "' because music not found in database");
        }
        return converMusicDataToMusicDataDto(musicById.get());
    }

    public List<MusicDataDto> getMusicByGenreName(String genreName) throws MusicDataNotFoundException {
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
                .soundid(musicData.getId())
                .coverSmallURL(musicData.getCover().getCoverSmallUrl())
                .coverMediumURL(musicData.getCover().getCoverMediumUrl())
                .durationSeconds(musicData.getDurationSeconds()).build();
    }

    public Boolean existMusicDataById(Long id) throws MusicDataNotFoundException {
        Boolean existsMusicDataById = musicDataRepository.existsMusicDataById(id);
        if (!existsMusicDataById) {
            throw new MusicDataNotFoundException("MusicData with id " + id + " not found");
        }
        return true;
    }
}