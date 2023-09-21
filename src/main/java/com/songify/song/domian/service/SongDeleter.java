package com.songify.song.domian.service;

import com.songify.song.domian.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SongDeleter {
    private final SongRepository songRepository;
    private final SongRetriever songRetriever;

    SongDeleter(SongRepository songRepository, SongRetriever songRetriever) {
        this.songRepository = songRepository;
        this.songRetriever = songRetriever;
    }

    public void deleteById(Long id) {
        songRetriever.existsById(id);
        log.info("deleting song with id: " + id);
        songRepository.deleteById(id);
    }
}
