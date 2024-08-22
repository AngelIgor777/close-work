package com.api.restmusicservice.dtos;
//
//import com.api.restmusicservice.entity.Author;
//import com.api.restmusicservice.entity.Cover;
//import com.api.restmusicservice.entity.Genre;
//import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MusicDataDto {
    private Long soundid;
    private String title;
    private String artistName;
    private String genre;
    private String musicUrl;
    private String coverSmallURL;
    private String coverMediumURL;
    private Integer durationSeconds;

}
