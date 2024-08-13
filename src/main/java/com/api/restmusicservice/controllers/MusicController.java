package com.api.restmusicservice.controllers;

import com.api.restmusicservice.entity.Music;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MusicController {

    @GetMapping
    public Music musicController() {
        return new Music();
    }
}
