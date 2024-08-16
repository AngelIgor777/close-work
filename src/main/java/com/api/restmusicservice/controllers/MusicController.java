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

    @GetMapping("/music/genre/classical")
    public List<MusicDataDto> getClassicMusic() {
        return musicService.classicalMusic();
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "HELLO WORLD";
    }


    @GetMapping("/music/genre/pop")
    public List<MusicDataDto> getPopMusic() {
        return musicService.popMusic();
    }


    @GetMapping("/music/genre/popular")
    public List<MusicDataDto> getPopularMusic() {
        return musicService.popularMusic();
    }

    @GetMapping("/music/genre/vocal")
    public List<MusicDataDto> getVocalMusic() {
        return musicService.vocalMusic();
    }


//    @GetMapping("/abc")
//    public String abc() {
//        return "Gabi I LOVE YOU!!! \n Жду тебя дома)))";
//    }

//    @GetMapping("/track/{trackid}")
//    public ResponseEntity<InputStreamResource> getMusicURLByTrackId(@PathVariable Long trackid) {
//        try {
//            InputStream musicStream = musicService.getMusicByTrackId(trackid);
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
//                    .body(new InputStreamResource(musicStream));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


    @GetMapping("/music/genre/rap")
    public ResponseEntity<List<MusicDataDto>> getRapMusic() {
        List<MusicDataDto> musicLocalList = musicService.getMusicLocalList();
        return ResponseEntity.ok(musicLocalList);
    }

    @GetMapping("/track/{id}")
    public ResponseEntity<Resource> getSong(@PathVariable long id) {
        List<MusicDataDto> musicLocalList = musicService.getMusicLocalList();
        Resource resource = null;
        for (MusicDataDto musicDataDto : musicLocalList) {
            if (musicDataDto.getSoundId().equals(String.valueOf(id))) {

                resource = new ClassPathResource(musicDataDto.getMusicUrlInResources());
            }
        }
        try {
            // Указываем путь относительно папки "resources"


            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("audio/mpeg"))
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
