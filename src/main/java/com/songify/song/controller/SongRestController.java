package com.songify.song.controller;

import com.songify.song.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.dto.request.CreateSongRequestDto;
import com.songify.song.dto.request.UpdateSongRequestDto;
import com.songify.song.dto.response.*;
import com.songify.song.error.SongNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongRestController {

    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("shawnmedes song1", "Shawn Mendes"),
            2, new Song("ariana grande song2", "ariana grande"),
            3, new Song("ariana grande song3","ariana grande" ),
            4, new Song("ariana grande song4", "ariana grande")
    ));

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, Song> limitedMap = database.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            GetAllSongsResponseDto response = new GetAllSongsResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = new GetAllSongsResponseDto(database);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Integer id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("song with id: " + id + " not found");
        }
        Song song = database.get(id);
        GetSongResponseDto response = new GetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        Song song = new Song(request.songName(), request.artist());
        log.info("adding new song: " + song);
        database.put(database.size() + 1, song);
        return ResponseEntity.ok(new CreateSongResponseDto(song));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Integer id) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("song with id: " + id + " not found");

        }
        database.remove(id);
        DeleteSongResponseDto deleteSongResponseDto = new DeleteSongResponseDto("song with id: " + id + " deleted", HttpStatus.OK);
        return ResponseEntity.ok(deleteSongResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Integer id,
                                                        @RequestBody @Valid UpdateSongRequestDto request) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("song with id: " + id + " not found");

        }
        String newSongName = request.songName();
        String newArtist = request.artist();
        Song newSong = new Song(newSongName, newArtist);
        Song oldSong = database.put(id, newSong);
        log.info("Update song with id: " + id +
                " whit oldSongName:" + oldSong.name() + " to newSongName: " +newSong.name() +
                " oldArtist: " + oldSong.artist() + " to newArtist: " + newSong.artist());
        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.name(), newSong.artist()));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Integer id,
                                                                              @RequestBody PartiallyUpdateSongRequestDto request){
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("song with id: " + id + " not found");
        }
        Song  songFromDatabase = database.get(id);
        Song.SongBuilder builder = Song.builder();
        if(request.song() != null){
            builder.name(request.song());
            log.info("partially updaten song name");
        } else{
            builder.name(songFromDatabase.name());
        }
        if(request.artist() != null) {
            builder.artist(request.artist());
            log.info("partially updaten song artist");
        } else{
            builder.artist(songFromDatabase.artist());
        }
        Song updatedSong  = builder.build();
        database.put(id, updatedSong);
        return ResponseEntity.ok(new PartiallyUpdateSongResponseDto(updatedSong));
    }
}