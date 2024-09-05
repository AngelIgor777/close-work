package com.api.restmusicservice.dtos;

import com.api.restmusicservice.entity.AllMusicGenresUrl;
import com.api.restmusicservice.wrappers.ResponseData;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) для представления данных о коллекции музыкальных треков.
 * <p>Используется для ограничения возвращаемого типа в {@code ResponseEntity<ResponseData>}</p>
 */
@Setter
public class AllMusicGenresUrlList implements ResponseData {

    private List<AllMusicGenresUrl> allMusicGenresUrls;

    public AllMusicGenresUrlList(List<AllMusicGenresUrl> allMusicGenresUrls) {
        this.allMusicGenresUrls = allMusicGenresUrls;
    }

    @JsonValue
    public List<AllMusicGenresUrl> getAllMusicGenresUrls() {
        return allMusicGenresUrls;
    }

}
