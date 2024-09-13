package com.api.restmusicservice.service;

import com.api.restmusicservice.converterstodto.ConverterMusicData;
import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.entity.MusicData;
import com.api.restmusicservice.exceptions.MusicDataNotFoundException;
import com.api.restmusicservice.repository.MusicDataRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicDataRepository musicDataRepository;
    private final ConverterMusicData converterMusicData;



    public MusicDataDto getMusicById(Long id) throws MusicDataNotFoundException {
        Optional<MusicData> musicById = musicDataRepository.findById(id);
        if (musicById.isEmpty()) {
            throw new MusicDataNotFoundException("Cannot return music with id '" + id + "' because music not found in database");
        }
        return converterMusicData.converMusicDataToMusicDataDto(musicById.get());
    }

    public List<MusicDataDto> getMusicByGenreName(String genreName) throws MusicDataNotFoundException {
        List<MusicDataDto> musicDataDtos = new LinkedList<>();
        List<MusicData> musicDataByGenreName = musicDataRepository.getMusicDataByGenreName(genreName);
        for (MusicData musicData : musicDataByGenreName) {
            musicDataDtos.add(converterMusicData.converMusicDataToMusicDataDto(musicData));
        }
        return musicDataDtos;
    }



    public Boolean existMusicDataById(Long id) throws MusicDataNotFoundException {
        Boolean existsMusicDataById = musicDataRepository.existsMusicDataById(id);
        if (!existsMusicDataById) {
            throw new MusicDataNotFoundException("MusicData with id " + id + " not found");
        }
        return true;
    }
}