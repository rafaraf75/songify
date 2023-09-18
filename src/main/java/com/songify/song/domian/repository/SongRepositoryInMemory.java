package com.songify.song.domian.repository;

import com.songify.song.domian.model.Song;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongRepositoryInMemory implements SongRepository {
    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("shawnmedes song1", "Shawn Mendes"),
            2, new Song("ariana grande song2", "ariana grande"),
            3, new Song("ariana grande song3","ariana grande" ),
            4, new Song("ariana grande song4", "ariana grande")
    ));

   public Song save(Song song) {
       database.put(database.size() + 1, song);
         return song;
   }
   public List<Song> findAll() {
       return database.values()
               .stream()
               .toList();
   }
}
