package com.api.restmusicservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "music_data", schema = "music")
public class MusicData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "title", nullable = false, length = 256)
    private String title;

    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    private Author artist;

    @ManyToOne
    @JoinColumn(name = "cover_url_id", referencedColumnName = "id")
    private Cover cover;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column(name = "music_url", nullable = false, length = 256)
    private String musicUrl;

    @Column(name = "duration_seconds", nullable = false)
    private Integer durationSeconds;

}

