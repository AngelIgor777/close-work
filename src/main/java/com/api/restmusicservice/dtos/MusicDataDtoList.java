package com.api.restmusicservice.dtos;

import com.api.restmusicservice.wrappers.ResponseData;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) для представления данных о коллекции музыкальных треков.
 * <p>Используется для ограничения возвращаемого типа в {@code ResponseEntity<ResponseData>}</p>
 */
@Setter
public class MusicDataDtoList implements ResponseData {

    private List<MusicDataDto> musicDataDtos;

    public MusicDataDtoList(List<MusicDataDto> musicDataDtos) {
        this.musicDataDtos = musicDataDtos;
    }
    @JsonValue
    public List<MusicDataDto> getMusicDataDtos() {
        return musicDataDtos;
    }

}
