package com.api.restmusicservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
//@Table(name = "music_data")
public class MusicData {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

//    @Column(name = "sound_id", nullable = false, length = 128)
    private String soundId;

//    @Column(name = "title", nullable = false, length = 256)
    private String title;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    private Author artist;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cover_url_id", referencedColumnName = "id")
    private Cover cover;

//    @Column(name = "music_url", nullable = false, length = 256)
    private String musicUrl;

//    @Column(name = "duration_seconds", nullable = false)
    private Integer durationSeconds;

}

