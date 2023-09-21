package com.songify.song.domian.service;

import com.songify.song.domian.model.Song;
import com.songify.song.domian.model.SongNotFoundException;
import com.songify.song.domian.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2


public class SongRetriever {
    private final SongRepository songRepository;

    SongRetriever(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    public List<Song> findAll() {
        log.info("Retrieving all songs: ");
        return songRepository.findAll();
    }
    public List<Song> findAllLimitedBy(Integer limit) {
        return songRepository.findAll()
                .stream().limit(limit)
                .toList();
    }

    public Song findSongById(Long id) {

        return songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("song with id: " + id + " not found"));
    }
    public void existsById(Long id) {
        if(!songRepository.existsById(id)) {
            throw new SongNotFoundException("song with id: " + id + " not found");
        }



    }

}
