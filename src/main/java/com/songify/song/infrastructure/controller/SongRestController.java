package com.songify.song.infrastructure.controller;

import com.songify.song.infrastructure.controller.error.SongMapper;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.domian.model.SongNotFoundException;
import com.songify.song.domian.model.Song;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
            GetAllSongsResponseDto response = new GetAllSongsResponseDto((List<Song>) limitedMap);
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = new GetAllSongsResponseDto((List<Song>) database);
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
        Song song = SongMapper.mapFromCreateSongRequestDtoToSongSong(request);
        log.info("adding new song: " + song);
        database.put(database.size() + 1, song);
        CreateSongResponseDto body = SongMapper.mapFromSongCreateSongResponseDto(song);
        return ResponseEntity.ok(body);
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
                " whit oldSongName:" + oldSong.getName() + " to newSongName: " +newSong.getName() +
                " oldArtist: " + oldSong.getArtist() + " to newArtist: " + newSong.getArtist());
        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.getName(), newSong.getArtist()));

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
            builder.name(songFromDatabase.getName());
        }
        if(request.artist() != null) {
            builder.artist(request.artist());
            log.info("partially updaten song artist");
        } else{
            builder.artist(songFromDatabase.getArtist());
        }
        Song updatedSong  = builder.build();
        database.put(id, updatedSong);
        return ResponseEntity.ok(new PartiallyUpdateSongResponseDto(updatedSong));
    }
}