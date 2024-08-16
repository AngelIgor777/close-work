package com.api.restmusicservice.service;

import com.api.restmusicservice.dtos.MusicDataDto;
//import com.api.restmusicservice.entity.Author;
//import com.api.restmusicservice.entity.Cover;
//import com.api.restmusicservice.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MusicService {
    public List<MusicDataDto> popularMusic() {
        return getMusicData("https://mp3party.net/pop-music");
    }

    public List<MusicDataDto> classicalMusic() {
        return getMusicData("https://mp3party.net/genres/classic");
    }

    public List<MusicDataDto> popMusic() {
        return getMusicData("https://mp3party.net/genres/pop");
    }

    public List<MusicDataDto> vocalMusic() {
        return getMusicData("https://mp3party.net/genres/vocal");
    }

    public List<MusicDataDto> rapMusic() {
        return getMusicData("https://mp3party.net/genres/rap");
    }


    private static void savemusic(Element track, List<MusicDataDto> musicDataList, String URL) {
        String songId = track.attr("data-a-song-id").replace("song-", "");
        String artistName = track.select("div.track__user-panel").attr("data-js-artist-name");
        String songTitle = track.select("div.track__user-panel").attr("data-js-song-title");
        String songUrl = track.select("div.track__user-panel").attr("data-js-url");
        String coverUrl = "https://mp3party.net" + track.select("div.track__user-panel").attr("data-js-image");
        String duration = track.select("div.track__info-item").first().text().replaceAll("[:]", "");
        String genre = URL.substring(URL.lastIndexOf("/") + 1);


        MusicDataDto musicDataDto = MusicDataDto.builder().title(songTitle).artistName(artistName).musicUrl(songUrl).soundId(songId).coverURL(Objects.requireNonNull(coverUrl)).genre(genre).durationSeconds(Integer.parseInt(duration)).build();
        musicDataList.add(musicDataDto);
    }

    public List<MusicDataDto> getMusicData(String URL) {
        ArrayList<MusicDataDto> musicDataArrayList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36").get();

            Elements tracks = doc.select("div.track.song-item");
            for (Element track : tracks) {
                int i = URL.lastIndexOf("/");
                // Извлечение данных
                savemusic(track, musicDataArrayList, URL);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return musicDataArrayList;
    }

    public InputStream getMusicByTrackId(Long trackId) throws IOException {
        // Шаг 1: Зайти на страницу жанра
        String genreUrl = "https://mp3party.net/genres/classic";
        HttpURLConnection genreConnection = (HttpURLConnection) new URL(genreUrl).openConnection();
        genreConnection.setRequestMethod("GET");
        genreConnection.setRequestProperty("User-Agent", "PostmanRuntime/7.41.1");

        // Шаг 2: Сохранить куки из ответа
        StringBuilder cookieBuilder = new StringBuilder();
        Map<String, List<String>> headers = genreConnection.getHeaderFields();
        if (headers.containsKey("Set-Cookie")) {
            for (String cookie : headers.get("Set-Cookie")) {
                if (cookieBuilder.length() > 0) {
                    cookieBuilder.append("; ");
                }
                cookieBuilder.append(cookie.split(";", 2)[0]);
            }
        }
        genreConnection.disconnect(); // Закрываем соединение с жанром

        // Шаг 3: Сделать запрос к треку с куками
        String mp3TrackURL = String.format("https://dl2.mp3party.net/online/%s.mp3", trackId);
        HttpURLConnection trackConnection = (HttpURLConnection) new URL(mp3TrackURL).openConnection();
        trackConnection.setRequestMethod("GET");
        trackConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        trackConnection.setRequestProperty("Cookie", cookieBuilder.toString()); // Используем собранные куки

        // Проверка на успешный ответ
        if (trackConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to get music. HTTP code: " + trackConnection.getResponseCode());
        }

        return trackConnection.getInputStream();
    }


    public List<MusicDataDto> getMusicLocalList()   {
        List<MusicDataDto> musicDataArrayList = new ArrayList<>();
        try {
            // Подготовка данных
//            ------------------------
            MusicDataDto musicData = new MusicDataDto();
            musicData.setSoundId("1");
            musicData.setTitle("zxcursed");
            musicData.setArtistName("waste");
            musicData.setGenre("Rap");
            musicData.setMusicUrl("http://147.45.245.154:8080/api/v1/track/1");// пример ссылки на обложку
            musicData.setDurationSeconds(240);
            musicData.setMusicUrlInResources("static/music/zxcursed - waste.mp3");
            musicDataArrayList.add(musicData);
//-------------------------------------------------
            //            ------------------------
            MusicDataDto musicData2 = new MusicDataDto();
            musicData2.setSoundId("2");
            musicData2.setTitle("Matrang");
            musicData2.setArtistName("Заманчивая");
            musicData2.setGenre("Rap");
            musicData2.setMusicUrl("http://147.45.245.154:8080/api/v1/track/2"); // пример ссылки на обложку
            musicData2.setDurationSeconds(240);
            musicData2.setMusicUrlInResources("static/music/Matrang - Заманчивая.mp3");
            musicDataArrayList.add(musicData2);
//-------------------------------------------------
            //            ------------------------
            MusicDataDto musicData3 = new MusicDataDto();
            musicData3.setSoundId("3");
            musicData3.setTitle("Matrang");
            musicData3.setArtistName("Провода");
            musicData3.setGenre("Pop");
            musicData3.setMusicUrl("http://147.45.245.154:8080/api/v1/track/3"); // пример ссылки на обложку
            musicData3.setDurationSeconds(240);
            musicData3.setMusicUrlInResources("static/music/Matrang - Провода.mp3");
            musicDataArrayList.add(musicData3);
//-------------------------------------------------
            //            ------------------------
            MusicDataDto musicData4 = new MusicDataDto();
            musicData4.setSoundId("4");
            musicData4.setTitle("Megan Moroney");
            musicData4.setArtistName("Indifferent");
            musicData4.setGenre("Rap");
            musicData4.setMusicUrl("http://147.45.245.154:8080/api/v1/track/4"); // пример ссылки на обложку
            musicData4.setDurationSeconds(240);
            musicData4.setMusicUrlInResources("static/music/Megan Moroney - Indifferent.mp3");
            musicDataArrayList.add(musicData4);
//-------------------------------------------------
            //            ------------------------
            MusicDataDto musicData5 = new MusicDataDto();
            musicData5.setSoundId("5");
            musicData5.setTitle("Sevak");
            musicData5.setArtistName("Жди меня там");
            musicData5.setGenre("Rap");
            musicData5.setMusicUrl("http://147.45.245.154:8080/api/v1/track/5"); // пример ссылки на обложку
            musicData5.setDurationSeconds(240);
            musicData5.setMusicUrlInResources("static/music/Sevak - Жди меня там.mp3");
            musicDataArrayList.add(musicData5);
//-------------------------------------------------
            //            ------------------------
            MusicDataDto musicData6 = new MusicDataDto();
            musicData6.setSoundId("6");
            musicData6.setTitle("Баста");
            musicData6.setArtistName("моя игра");
            musicData6.setGenre("Rap");
            musicData6.setMusicUrl("http://147.45.245.154:8080/api/v1/track/6"); // пример ссылки на обложку
            musicData6.setDurationSeconds(240);
            musicData6.setMusicUrlInResources("static/music/Баста - моя игра.mp3");
            musicDataArrayList.add(musicData6);
//-------------------------------------------------
            //            ------------------------
            MusicDataDto musicData7 = new MusicDataDto();
            musicData7.setSoundId("7");
            musicData7.setTitle("Вика Старикова");
            musicData7.setArtistName("Три Желания");
            musicData7.setGenre("Rap");
            musicData7.setMusicUrl("http://147.45.245.154:8080/api/v1/track/7"); // пример ссылки на обложку
            musicData7.setDurationSeconds(240);
            musicData7.setMusicUrlInResources("static/music/Вика Старикова - Три желания.mp3");
            musicDataArrayList.add(musicData7);
//-------------------------------------------------
            //            ------------------------
            MusicDataDto musicData8 = new MusicDataDto();
            musicData8.setSoundId("8");
            musicData8.setTitle("Моя Мишель");
            musicData8.setArtistName("Зима в сердце");
            musicData8.setGenre("Rap");
            musicData8.setMusicUrl("http://147.45.245.154:8080/api/v1/track/8"); // пример ссылки на обложку
            musicData8.setDurationSeconds(240);
            musicData8.setMusicUrlInResources("static/music/Моя Мишель - Зима в сердце.mp3");
            musicDataArrayList.add(musicData8);
//-------------------------------------------------
            //            ------------------------
            MusicDataDto musicData9 = new MusicDataDto();
            musicData9.setSoundId("9");
            musicData9.setTitle("Элджей");
            musicData9.setArtistName("Ecstasy");
            musicData9.setGenre("Rap");
            musicData9.setMusicUrlInResources("static/music/zxcursed - waste.mp3");
            musicData9.setMusicUrl("http://147.45.245.154:8080/api/v1/track/9"); // пример ссылки на обложку
            musicData9.setDurationSeconds(240);
            musicData9.setMusicUrlInResources("static/music/Элджей - Ecstasy.mp3");
            musicDataArrayList.add(musicData9);
//-------------------------------------------------
            //            ------------------------
            MusicDataDto musicData10 = new MusicDataDto();
            musicData10.setSoundId("10");
            musicData10.setTitle("Яна");
            musicData10.setArtistName("Одинокий голубь");
            musicData10.setGenre("Rap");
            musicData10.setMusicUrl("http://147.45.245.154:8080/api/v1/track/10"); // пример ссылки на обложку
            musicData10.setDurationSeconds(240);
            musicData10.setMusicUrlInResources("static/music/Яна - Одинокий голубь.mp3");
            musicDataArrayList.add(musicData10);
//-------------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musicDataArrayList;
    }
}
