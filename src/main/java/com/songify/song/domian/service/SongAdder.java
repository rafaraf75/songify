package com.songify.song.domian.service;

import com.songify.song.domian.model.Song;
import com.songify.song.domian.repository.SongRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
public class SongAdder {
    private final SongRepository songRepository;
    SongAdder(SongRepository songRepository) {
        this.songRepository = songRepository;

    }
    public Song addSong(Song song) {
        log.info("adding new song: " + song);
        return songRepository.save(song);

    }

}
