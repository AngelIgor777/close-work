package com.api.restmusicservice.controllers;

import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MusicController {
    private final MusicService musicService;


    @GetMapping("/music/genre/{genre}")
    public List<MusicDataDto> getPopularMusic(@PathVariable("genre") String genre) {
        if (genre.equals("popular")) {
            return musicService.getMusicByGenreName("");
        } else if (genre.equals("rap")) {
            return musicService.getMusicByGenreName("rap");
        } else if (genre.equals("pop")) {
            return musicService.getMusicByGenreName("pop");
        } else if (genre.equals("club")) {
            return musicService.getMusicByGenreName("club");
        } else if (genre.equals("classic")) {
            return musicService.getMusicByGenreName("classic");
        }
        return null;
    }

    @GetMapping("/track/{id}")
    public MusicDataDto getMusicById(@PathVariable("id") Long id) {
        return musicService.getMusicById(id);
    }
}
