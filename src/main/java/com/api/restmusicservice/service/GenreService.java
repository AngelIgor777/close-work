package com.api.restmusicservice.service;

import com.api.restmusicservice.converterstodto.ConverterMusicData;
import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.dtos.MusicDataDtoList;
import com.api.restmusicservice.entity.MusicData;
import com.api.restmusicservice.exceptions.ErrorResponse;
import com.api.restmusicservice.exceptions.MusicDataNotFoundException;
import com.api.restmusicservice.repository.MusicDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Сервис для работы с музыкальными данными, предоставляющий методы для получения данных о музыке по жанру.
 *
 * <p>Этот класс использует {@link MusicDataRepository} для извлечения данных из базы данных и {@link ConverterMusicData}
 * для преобразования сущностей {@link MusicData} в объекты {@link MusicDataDto}.</p>
 */
@RequiredArgsConstructor
@Service
public class GenreService {
    private final MusicDataRepository musicDataRepository;
    private final ConverterMusicData converterMusicData;


    /**
     * Получает список музыкальных данных по заданному жанру.
     *
     * <p>Если жанр действителен, возвращает список объектов {@link MusicDataDto} обёрнутых в {@link MusicDataDtoList}.
     * Если жанр недействителен, возвращает ошибку {@link MusicDataNotFoundException}.</p>
     *
     * @param genre жанр музыки для поиска. Должен быть одним из следующих: "popular", "rap", "pop", "club", "classic", "instrumental", "rock", "tiktok".
     * @return {@link ResponseEntity} с объектом {@link MusicDataDtoList} и HTTP статусом {@code 200 OK} при успешном выполнении запроса,
     * либо с объектом {@link ErrorResponse} и HTTP статусом {@code 404 Not Found} при ошибке.
     * @throws MusicDataNotFoundException если жанр недействителен.
     */
    public List<MusicDataDto> getMusicDataDtosByGenreName(String genre) {
        try {
            List<MusicDataDto> musicDataDtos = switch (genre) {
                case "popular" -> getMusicByGenreName("popular");
                case "rap" -> getMusicByGenreName("rap");
                case "pop" -> getMusicByGenreName("pop");
                case "club" -> getMusicByGenreName("club");
                case "classic" -> getMusicByGenreName("classic");
                case "instrumental" -> getMusicByGenreName("instrumental");
                case "rock" -> getMusicByGenreName("rock");
                case "tiktok" -> getMusicByGenreName("music-from-tik-tok");
                default -> throw new MusicDataNotFoundException("Invalid genre: " + genre);
            };
            return musicDataDtos;
        } catch (MusicDataNotFoundException musicDataNotFoundException) {
            throw new RuntimeException(musicDataNotFoundException.getMessage());
        }
    }

    /**
     * Получает список музыкальных данных по названию жанра.
     *
     * <p>Преобразует сущности {@link MusicData} в объекты {@link MusicDataDto}.</p>
     *
     * @param genreName название жанра для поиска.
     * @return список объектов {@link MusicDataDto} для указанного жанра.
     * @throws MusicDataNotFoundException если не найдено данных для указанного жанра.
     */
    public List<MusicDataDto> getMusicByGenreName(String genreName) throws MusicDataNotFoundException {
        List<MusicDataDto> musicDataDtos = new LinkedList<>();
        List<MusicData> musicDataByGenreName = musicDataRepository.getMusicDataByGenreName(genreName);
        for (MusicData musicData : musicDataByGenreName) {
            musicDataDtos.add(converterMusicData.converMusicDataToMusicDataDto(musicData));
        }
        return musicDataDtos;
    }
}
