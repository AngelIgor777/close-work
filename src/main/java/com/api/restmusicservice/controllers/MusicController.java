package com.api.restmusicservice.controllers;

import com.api.restmusicservice.dtos.MusicDataDto;
import com.api.restmusicservice.service.*;
import com.api.restmusicservice.wrappers.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Контроллер {@code MusicController} предоставляет API для взаимодействия с музыкальными данными.
 *
 * <p>Обрабатывает запросы, связанные с жанрами музыки, треками и поиском музыки.</p>
 *
 * <p>Маршрут API: {@code /api/v1}</p>
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class MusicController {

    private final GenreService genreService;
    private final TrackService trackService;
    private final SearchService searchService;
    private final AllMusicGenresUrlService allMusicGenresUrlService;
    private final UserMusicService userMusicService;
    private final AllMusicService allMusicService;

    /**
     * Получает музыку по указанному жанру.
     *
     * <p>Этот метод возвращает список объектов {@link com.api.restmusicservice.dtos.MusicDataDto}
     * для указанного жанра. Если жанр не найден, будет выброшено исключение {@link com.api.restmusicservice.exceptions.MusicDataNotFoundException}.</p>
     *
     * @param genre жанр музыки для поиска. Должен соответствовать одному из жанров, обрабатываемых методом {@link GenreService#getMusicDataDtosByGenreName(String)}.
     * @return объект {@code ResponseEntity<ResponseData>} с данными о музыке по указанному жанру.
     * @throws com.api.restmusicservice.exceptions.MusicDataNotFoundException если жанр не существует.
     */
    @GetMapping("music/genre/{genre}")
    public ResponseEntity<ResponseData> getPopularMusic(@PathVariable("genre") String genre
            , @RequestHeader("Authorization") String token
    ) {
        log.info("Запрос -> 'music/genre/{genre}'");

        // Извлекаем токен без префикса "Bearer "
        String userToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        List<MusicDataDto> musicDataDtosByGenreName = genreService.getMusicDataDtosByGenreName(genre);
        ResponseEntity<ResponseData> userMusicByUserId = userMusicService.getUserMusicByUserId(musicDataDtosByGenreName, userToken);
        log.info("Ответ <- 'music/genre/{genre}'");
        return userMusicByUserId;
    }


    /**
     * Получает информацию о треке по его уникальному идентификатору.
     *
     * <p>Этот метод возвращает объект {@link com.api.restmusicservice.dtos.MusicDataDto}
     * для трека с указанным идентификатором. Если трек не найден, будет выброшено исключение {@link com.api.restmusicservice.exceptions.MusicDataNotFoundException}.</p>
     *
     * @param id уникальный идентификатор трека.
     * @return объект {@code ResponseEntity<ResponseData>} с данными о треке.
     * @throws com.api.restmusicservice.exceptions.MusicDataNotFoundException если трек с указанным идентификатором не найден.
     */
    @GetMapping("/track/{id}")
    public ResponseEntity<ResponseData> getMusicById(@PathVariable("id") Long id) {
        log.info("Запрос  -> '/track/{id}'");

        ResponseData trackById = trackService.getTrackById(id);
        ResponseEntity<ResponseData> responseEntityMusicDataDto = new ResponseEntity<>(trackById, HttpStatus.OK);

        log.info("Ответ  <- '/track/{id}'");
        return responseEntityMusicDataDto;
    }

    @PostMapping("/musicsById")
    public ResponseEntity<List<ResponseData>> getMusicsById(@RequestBody List<Long> musicsId) {
        log.info("Request to '/musicsById'");

        List<ResponseData> musicsByMusicsId = userMusicService.getMusicsByMusicsId(musicsId);
        return new ResponseEntity<>(musicsByMusicsId, HttpStatus.OK);
    }

    /**
     * Проверяет существование трека по его уникальному идентификатору.
     *
     * <p>Этот метод используется для проверки наличия трека и для добавления трека в избранное пользователем.</p>
     *
     * @param id уникальный идентификатор трека.
     * @return объект {@code ResponseEntity<ResponseData>} с данными о существовании трека. В классе-обёртке {@link com.api.restmusicservice.dtos.ExistMusic}
     * содержится поле {@code Boolean existMusic}.
     */
    @GetMapping("/existTrack/{id}")
    public ResponseEntity<ResponseData> musicExistById(@PathVariable("id") Long id) {
        log.info("Request to '/existTrack/{id}'");

        return trackService.existTrackById(id);
    }

    /**
     * Выполняет поиск музыки по заданному запросу.
     *
     * <p>Этот метод возвращает список объектов {@link com.api.restmusicservice.dtos.MusicDataDto}
     * соответствующих запросу. Если данных нет, возвращается пустой список.</p>
     *
     * @param query строка запроса для поиска музыки. Может содержать имя исполнителя, название песни и т.д.
     * @return объект {@code ResponseEntity<ResponseData>} с результатами поиска. В случае отсутствия данных возвращается пустой список.
     */
    @GetMapping("/findMusic/{query}")
    public ResponseEntity<ResponseData> getMusicByQuery(@PathVariable("query") String query) {
        log.info("Request to '/findMusic/{query}'");

        return searchService.searchMusic(query);
    }

    @GetMapping("/music/allMusicGenres")
    public ResponseEntity<ResponseData> getAllMusicGenres() {
        log.info("Request to '/music/allMusicGenres'");

        return allMusicGenresUrlService.getAllMusicGenreUrls();
    }


    @GetMapping("music/allMusic")
    public List<MusicDataDto> getAllMusic(@RequestParam(required = false, defaultValue = "0") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size,
                                          @RequestHeader("Authorization") String token) {
        log.info("Request to 'music/allMusic'");


        // Извлекаем токен без префикса "Bearer "
        String userToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        // Получаем всю музыку без учета пагинации
        List<MusicDataDto> allMusic = allMusicService.getAllMusic(); // Предполагается, что это весь список

        // Фильтруем по UserId
        List<MusicDataDto> allMusicByUserId = allMusicService.getAllMusicByUserId(allMusic, userToken);

        // Перемешиваем список
        Collections.shuffle(allMusicByUserId);

        // Реализуем пагинацию вручную
        int start = Math.min(page * size, allMusicByUserId.size());
        int end = Math.min(start + size, allMusicByUserId.size());

        // Возвращаем нужную страницу
        return allMusicByUserId.subList(start, end);
    }

}
