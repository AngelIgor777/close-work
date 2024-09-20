package com.api.restmusicservice.dtos;

import lombok.Data;

/**
 * Класс представляет собой обёртку для ответа пользователю
 * о существовании данных песен.
 * <p>Используется для ограничения возвращаемого типа в {@code ResponseEntity<ResponseData>}</p>
 */
@Data
public class ExistMusic  {

    /**
     * @value поле описывает наличие песни в базе данных
     */
    private Boolean existMusic;

    /**
     * @param existMusic-наличие песни в базе данных
     */
    public ExistMusic(Boolean existMusic) {
        this.existMusic = existMusic;
    }
}
