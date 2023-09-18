package com.songify.song.domian.service;

import com.songify.song.domian.model.Song;
import com.songify.song.domian.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
@Log4j2


public class SongRetriver {
    private final SongRepository songRepository;

    SongRetriver(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    public List<Song> findAll() {
        log.info("Retrieving all songs");
        return songRepository.findAll();
    }
    public Map<Integer, Song> findAllLimitedBy(Integer limit) {
        return (Map<Integer, Song>) songRepository.findAll()
                .stream().limit(limit)
                .toList();



    }
}
