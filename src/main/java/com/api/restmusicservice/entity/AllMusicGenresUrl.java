package com.api.restmusicservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "all_music_genres_urls",schema = "music")
public class AllMusicGenresUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "all_music_genres_url", length = 512)
    private String allMusicGenresUrl;

    @Column(name = "cover_url", length = 512)
    private String coverUrl;

    @Column(name = "genre_name", length = 128)
    private String genreName;
}