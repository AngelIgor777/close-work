package com.api.restmusicservice.dtos;

import com.api.restmusicservice.wrappers.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object (DTO) для представления данных о музыкальном треке.
 *
 * <p>Этот класс используется для передачи информации о музыкальных треках между слоями приложения.</p>
 * <p>Используется для ограничения возвращаемого типа в {@code ResponseEntity<ResponseData>}</p>
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MusicDataDto implements ResponseData {
    private Long soundid;
    private String playerSoundId = "";
    private Boolean stateLike;
    private String title;
    private String artistName;
    private String genre;
    private String musicUrl;
    private String coverSmallURL;
    private String coverMediumURL;
    private Integer durationSeconds;

}
