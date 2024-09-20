package com.api.restmusicservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Используется для ответа пользователю в случае возникновения исключения {@link MusicDataNotFoundException}.
 *
 * <p>Этот класс предоставляет информацию о типе и сообщении исключения.</p>
 * <p>Используется для ограничения возвращаемого типа в {@code ResponseEntity<ResponseData>}</p>

 */
@AllArgsConstructor
@Data
public class ErrorResponse{
    private String exception;
    private String message;
}
