package com.api.restmusicservice.converterstodto;

import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.entity.MusicData;
import org.springframework.stereotype.Service;


/**
 * Класс для конвертации {@link MusicData} в {@link MusicDataDto}
 */
@Service
public class ConverterMusicData {
    public MusicDataDto converMusicDataToMusicDataDto(MusicData musicData) {
        return MusicDataDto.builder().musicUrl(musicData.getMusicUrl())
                .genre(musicData.getGenre().getName())
                .title(musicData.getTitle())
                .artistName(musicData.getArtist().getAuthorName())
                .soundid(musicData.getId())
                .coverSmallURL(musicData.getCover().getCoverSmallUrl())
                .coverMediumURL(musicData.getCover().getCoverMediumUrl())
                .durationSeconds(musicData.getDurationSeconds()).build();
    }
}
