package com.songify.song.domian.repository;

import com.songify.song.domian.model.Song;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface SongRepository extends Repository<Song, Long> {
    Song save(Song song);
    List<Song> findAll();
}