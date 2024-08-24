package com.api.restmusicservice.controllers;

import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.exceptions.ErrorResponse;
import com.api.restmusicservice.exceptions.MusicDataNotFoundException;
import com.api.restmusicservice.service.MusicService;
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
    private final MusicService musicService;


    @GetMapping("music/genre/{genre}")
    public ResponseEntity<?> getPopularMusic(@PathVariable("genre") String genre) {
        try {
            List<MusicDataDto> musicDataDtos = switch (genre) {
                case "popular" -> musicService.getMusicByGenreName("popular");
                case "rap" -> musicService.getMusicByGenreName("rap");
                case "pop" -> musicService.getMusicByGenreName("pop");
                case "club" -> musicService.getMusicByGenreName("club");
                case "classic" -> musicService.getMusicByGenreName("classic");
                default -> throw new MusicDataNotFoundException("Invalid genre: " + genre);
            };
            return new ResponseEntity<>(musicDataDtos, HttpStatus.OK);

        } catch (MusicDataNotFoundException musicDataNotFoundException) {
            ErrorResponse errorResponse = new ErrorResponse(musicDataNotFoundException.getClass().getSimpleName(), musicDataNotFoundException.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/track/{id}")
    public ResponseEntity<?> getMusicById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(musicService.getMusicById(id), HttpStatus.OK);
        } catch (MusicDataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/existMusic/{id}")
    public ResponseEntity<?> musicExistById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(musicService.existMusicDataById(id), HttpStatus.OK);
        } catch (MusicDataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
