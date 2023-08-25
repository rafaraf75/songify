package com.songify;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class SongsController {

    Map<Integer, String> database = new HashMap<>(Map.of(
            1, "shawnmendes song1",
            2, "ariana grande song2",
            3, "ariana grande song21154",
            4, "ariana grande song21234567"
    ));

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs(@RequestParam(required = false) Integer limit, @RequestHeader String requestId) {

        if (limit != null) {
            Map<Integer, String> limiteMap = database.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
            SongResponseDto response = new SongResponseDto(limiteMap);
            return ResponseEntity.ok(response);
        }
        SongResponseDto response = new SongResponseDto(database);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongById(@PathVariable Integer id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        String song = database.get(id);
        if (song == null) {
            return ResponseEntity.notFound().build();
        }
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/songs")
    public ResponseEntity<SingleSongResponseDto> postSong(@RequestBody SongRequestDto request){
        String songName = request.songName();
        log.info("adding new song: "+songName);
        database.put(database.size()+1,songName);
        return ResponseEntity.ok(new SingleSongResponseDto(songName));
    }

}