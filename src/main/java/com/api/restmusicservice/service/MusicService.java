package com.api.restmusicservice.service;

import com.api.restmusicservice.entity.Author;
import com.api.restmusicservice.entity.Cover;
import com.api.restmusicservice.entity.MusicData;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicService {

    public List<MusicData> popularMusic() {
        return getMusicData("https://mp3party.net/pop-music");
    }

    public List<MusicData> classicalMusic() {
        return getMusicData("https://mp3party.net/genres/classic");
    }

    public List<MusicData> popMusic() {
        return getMusicData("https://mp3party.net/genres/pop");
    }

    public List<MusicData> vocalMusic() {
        return getMusicData("https://mp3party.net/genres/vocal");
    }

    public List<MusicData> rapMusic() {
        return getMusicData("https://mp3party.net/genres/rap");
    }


    private static void savemusic(Element track, List<MusicData> musicDataList) {
        String songId = track.attr("data-a-song-id").replace("song-", "");
        String artistName = track.select("div.track__user-panel").attr("data-js-artist-name");
        String songTitle = track.select("div.track__user-panel").attr("data-js-song-title");
        String songUrl = track.select("div.track__user-panel").attr("data-js-url");
        String coverUrl = "https://mp3party.net" + track.select("div.track__user-panel").attr("data-js-image");
        String duration = track.select("div.track__info-item").first().text().replaceAll("[:]", "");

        Author author = Author.builder().authorName(artistName).build();
        Cover cover = Cover.builder().coversUrl(coverUrl).build();
        if (cover.getCoversUrl().equals("https://mp3party.net"))
            cover = null;
        MusicData musicData = MusicData.builder()
                .title(songTitle)
                .artist(author)
                .musicUrl(songUrl)
                .soundId(songId)
                .cover(cover)
                .durationSeconds(Integer.parseInt(duration))
                .build();
        musicDataList.add(musicData);
    }

    public List<MusicData> getMusicData(String URL) {
        ArrayList<MusicData> musicDataArrayList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL).get();

            Elements tracks = doc.select("div.track.song-item");
            for (Element track : tracks) {
                // Извлечение данных
                savemusic(track, musicDataArrayList);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return musicDataArrayList;
    }
}
