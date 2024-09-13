package com.api.restmusicservice.service;

import com.api.restmusicservice.converterstodto.ConverterMusicData;
import com.api.restmusicservice.dtos.ExistMusic;
import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.entity.MusicData;
import com.api.restmusicservice.exceptions.ErrorResponse;
import com.api.restmusicservice.exceptions.MusicDataNotFoundException;
import com.api.restmusicservice.repository.MusicDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для работы с музыкальными треками.
 *
 * <p>Этот класс предоставляет методы для получения информации о треке по идентификатору и проверки существования трека.</p>
 */
@Service
@RequiredArgsConstructor
public class TrackService {
    private final MusicDataRepository musicDataRepository;
    private final ConverterMusicData converterMusicData;

    /**
     * Получает информацию о музыкальном треке по его уникальному идентификатору.
     *
     * <p>Если трек с указанным идентификатором найден в базе данных, он преобразуется в объект {@link MusicDataDto} и возвращается
     * с HTTP статусом {@code 200 OK}. Если трек не найден, выбрасывается {@link MusicDataNotFoundException}, и возвращается
     * {@link ErrorResponse} с HTTP статусом {@code 404 Not Found}.</p>
     *
     * @param id уникальный идентификатор музыкального трека.
     * @return {@link ResponseEntity} с объектом {@link MusicDataDto} и HTTP статусом {@code 200 OK}, если трек найден,
     * или с объектом {@link ErrorResponse} и HTTP статусом {@code 404 Not Found}, если трек не найден.
     * @throws MusicDataNotFoundException
     */
    public MusicDataDto getTrackById(Long id) {
        Optional<MusicData> musicData = musicDataRepository.findById(id);
        if (musicData.isPresent()) {
            return converterMusicData.converMusicDataToMusicDataDto(musicData.get());
        } else {
            throw new MusicDataNotFoundException("Песня с ID '" + id + "' не найдена");
        }
    }

    /**
     * Проверяет существование музыкального трека по его уникальному идентификатору.
     *
     * @param id уникальный идентификатор музыкального трека.
     * @return {@link ResponseEntity} с объектом {@link ExistMusic} и HTTP статусом {@code 200 OK},
     * если {@link ExistMusic} имеет поле {@code existMusic} равное {@code true}.
     * Иначе {@code 404 NOT FOUND}.
     */
    public ExistMusic existTrackById(Long id) {
        Boolean existsMusicDataById = musicDataRepository.existsMusicDataById(id);
        if (existsMusicDataById) {
            return new ExistMusic(existsMusicDataById);
        } else {
            throw new MusicDataNotFoundException("Музыка с ID '" + id + "' не найден");
        }
    }
}
