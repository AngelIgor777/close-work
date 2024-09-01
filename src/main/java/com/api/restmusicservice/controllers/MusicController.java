package com.api.restmusicservice.controllers;

import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.entity.MusicData;
import com.api.restmusicservice.exceptions.ErrorResponse;
import com.api.restmusicservice.exceptions.MusicDataNotFoundException;
import com.api.restmusicservice.service.GenreService;
import com.api.restmusicservice.service.MusicService;
import com.api.restmusicservice.service.SearchService;
import com.api.restmusicservice.service.TrackService;
import com.api.restmusicservice.wrappers.ResponseEntityWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MusicController {
    private final GenreService genreService;
    private final TrackService trackService;
    private final SearchService searchService;

    @GetMapping("music/genre/{genre}")
    public ResponseEntity<ResponseEntityWrapper> getPopularMusic(@PathVariable("genre") String genre) {
        return genreService.getMusicDataDtosByGenreName(genre);
    }

    @GetMapping("/track/{id}")
    public ResponseEntity<ResponseEntityWrapper> getMusicById(@PathVariable("id") Long id) {
        return trackService.getTrackById(id);
    }

    @GetMapping("/existTrack/{id}")
    public ResponseEntity<ResponseEntityWrapper> musicExistById(@PathVariable("id") Long id) {
        return trackService.existTrackById(id);
    }

    @GetMapping("/findMusic/{query}")
    public ResponseEntity<ResponseEntityWrapper> getMusicByQuery(@PathVariable("query") String query) {
        List<MusicDataDto> musicData = searchService.searchMusic(query);
        ResponseEntityWrapper responseEntityWrapperSearchMusicDto = new ResponseEntityWrapper(musicData);
        return new ResponseEntity<>(responseEntityWrapperSearchMusicDto, HttpStatus.OK);
    }


}
