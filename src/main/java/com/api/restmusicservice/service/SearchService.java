package com.api.restmusicservice.service;

import com.api.restmusicservice.converterstodto.ConverterMusicData;
import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.dtos.MusicDataDtoList;
import com.api.restmusicservice.entity.MusicData;
import com.api.restmusicservice.repository.MusicDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для поиска музыкальных данных по запросу.
 *
 * <p>Этот класс использует {@link MusicDataRepository} для извлечения данных из базы данных и {@link ConverterMusicData}
 * для преобразования сущностей {@link MusicData} в объекты {@link MusicDataDto}.</p>
 */
@Service
@RequiredArgsConstructor
public class SearchService {

    private final MusicDataRepository musicDataRepository;
    private final ConverterMusicData converterMusicData;

    /**
     * Выполняет поиск музыкальных данных по запросу.
     *
     * <p>Поиск выполняется по названию трека и имени исполнителя. Результаты поиска из двух источников объединяются в один список.
     * Результаты преобразуются в объекты {@link MusicDataDto} и возвращаются в виде {@link MusicDataDtoList}.</p>
     *
     * @param query строка запроса для поиска. Может содержать фрагменты имени исполнителя или названия трека.
     * @return {@link ResponseEntity} с объектом {@link MusicDataDtoList} и HTTP статусом {@code 200 OK}.
     * Если нет результатов, возвращается пустой список.
     */
    public List<MusicDataDto> searchMusic(String query) {
        List<MusicData> musicDataByArtistContainingIgnoreCase =
                musicDataRepository.findMusicDataByArtist_AuthorNameContainingIgnoreCase(query);
        List<MusicData> musicDataByTitleContainingIgnoreCase = musicDataRepository.findMusicDataByTitleContainingIgnoreCase(query);

        // Создаем новый список, чтобы объединить результаты
        List<MusicData> combinedResults = new ArrayList<>(musicDataByArtistContainingIgnoreCase);
        combinedResults.addAll(musicDataByTitleContainingIgnoreCase);

        // Возвращаем объединенный список
        return combinedResults.stream()
                .map(converterMusicData::converMusicDataToMusicDataDto)
                .toList();


    }
}
