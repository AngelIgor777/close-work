package com.api.restmusicservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
//@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Table(name = "authors")
public class Author {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

//    @Column(name = "author_name", nullable = false, unique = true, length = 256)
    private String authorName;

//    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<MusicData> musicData;

}
