package com.api.restmusicservice.repository;

import com.api.restmusicservice.entity.MusicData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicDataRepository extends JpaRepository<MusicData, Long> {
    List<MusicData> getMusicDataByGenreName(String genreName);
}
