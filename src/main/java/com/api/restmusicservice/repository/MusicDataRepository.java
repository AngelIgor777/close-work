package com.api.restmusicservice.repository;

import com.api.restmusicservice.entity.Author;
import com.api.restmusicservice.entity.MusicData;
import com.api.restmusicservice.exceptions.MusicDataNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicDataRepository extends JpaRepository<MusicData, Long> {
    List<MusicData> getMusicDataByGenreName(String genreName) throws MusicDataNotFoundException;

    Boolean existsMusicDataById(Long id);

    List<MusicData> findMusicDataByTitleContainingIgnoreCase(String title);

    List<MusicData> findMusicDataByArtist_AuthorNameContainingIgnoreCase(String author);
}
