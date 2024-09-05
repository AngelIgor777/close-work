package com.api.restmusicservice.service;

import com.api.restmusicservice.dtos.AllMusicGenresUrlList;
import com.api.restmusicservice.entity.AllMusicGenresUrl;
import com.api.restmusicservice.repository.AllMusicGenresUrlRepository;
import com.api.restmusicservice.wrappers.ResponseData;
import lombok.Data;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class AllMusicGenresUrlService {
    private final AllMusicGenresUrlRepository allMusicGenresUrlRepository;

    public ResponseEntity<ResponseData> getAllMusicGenreUrls() {
        List<AllMusicGenresUrl> allMusicGenresUrls = allMusicGenresUrlRepository.findAll();
        AllMusicGenresUrlList allMusicGenresUrlList = new AllMusicGenresUrlList(allMusicGenresUrls);
        return new ResponseEntity<>(allMusicGenresUrlList, HttpStatus.OK);
    }
}
