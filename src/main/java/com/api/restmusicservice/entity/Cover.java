package com.api.restmusicservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "covers", schema = "music")
public class Cover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cover_small_url", nullable = false, length = 256)
    private String coverSmallUrl;

    @Column(name = "covers_medium_url", nullable = false, length = 256)
    private String coverMediumUrl;

    @OneToMany(mappedBy = "cover")
    private Set<MusicData> musicData;
}
