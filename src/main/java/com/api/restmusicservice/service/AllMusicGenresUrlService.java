package com.api.restmusicservice.service;

import com.api.restmusicservice.entity.AllMusicGenresUrl;
import com.api.restmusicservice.repository.AllMusicGenresUrlRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class AllMusicGenresUrlService {
    private final AllMusicGenresUrlRepository allMusicGenresUrlRepository;

    public List<AllMusicGenresUrl> getAllMusicGenreUrls() {
        return allMusicGenresUrlRepository.findAll();
    }
}
