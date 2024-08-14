package com.api.restmusicservice.controllers;

import com.api.restmusicservice.entity.MusicData;
import com.api.restmusicservice.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MusicController {
    private final MusicService musicService;


    private final String musicDirectory = "/path/to/your/music/files";

    @GetMapping("/play/{filename}")
    public ResponseEntity<InputStreamResource> streamMusic(@PathVariable("filename") String filename) throws IOException {
        File file = new File(musicDirectory + File.separator + filename);

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(resource);
    }

    @GetMapping("/popularMusic")
    public List<MusicData> getPopularMusic() {
        return musicService.popularMusic();
    }

    @GetMapping("/classicMusic")
    public List<MusicData> getClassicMusic() {
        return musicService.classicalMusic();
    }

    @GetMapping("/popMusic")
    public List<MusicData> getPopMusic() {
        return musicService.popMusic();
    }

    @GetMapping("/rapMusic")
    public List<MusicData> rapPopMusic() {
        return musicService.rapMusic();
    }
}
