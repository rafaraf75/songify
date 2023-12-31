package com.songify.song.infrastructure.controller.error;

import com.songify.song.domian.model.Song;
import com.songify.song.domian.service.SongAdder;
import com.songify.song.domian.service.SongDeleter;
import com.songify.song.domian.service.SongRetriever;
import com.songify.song.domian.service.SongUpdater;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.songify.song.infrastructure.controller.error.SongMapper.*;

@RestController
@Log4j2
@RequestMapping("/songs")
@AllArgsConstructor
public class SongRestController {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;


    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<Song> allSongs = songRetriever.findAll(pageable);

        GetAllSongsResponseDto response = mapFromSongToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Long id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        Song song = songRetriever.findSongById(id);
        GetSongResponseDto response = mapFromSongToGetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        Song song = mapFromCreateSongRequestDtoToSongSong(request);
        Song savedSong = songAdder.addSong(song);
        CreateSongResponseDto body = mapFromSongToCreateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Long id) {
        songDeleter.deleteById(id);
        DeleteSongResponseDto body = mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Long id,
                                                        @RequestBody @Valid UpdateSongRequestDto request) {
        Song newSong = mapFromUpdateSongRequestDtoToSong(request);
       songUpdater.updateById(id, newSong);
        UpdateSongResponseDto body = mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(body);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Long id,
                                                                              @RequestBody PartiallyUpdateSongRequestDto request) {
        Song updatedSong = mapFromPartiallyUpdateSongRequestDtoToSong(request);
        Song savedSong = songUpdater.updatePartiallyById(id, updatedSong);
        PartiallyUpdateSongResponseDto body = mapFromSongToPartiallyUpdateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }
    // Dirty checking version
    // public void updateById(Long id, Song newSong) {
    //     Song songById = songRetriever.findSongById(id);
    //     songById.setName(newSong.getName());
    //     songById.setArtist(newSong.getArtist());
    // }

    // public Song updatePartiallyById(Long id, Song songFromRequest) {
    //     Song songFromDatabase = songRetriever.findSongById(id);
    //     if (songFromRequest.getName() != null) {
    //         songFromDatabase.setName(songFromRequest.getName());
    //     }
    //     if (songFromRequest.getArtist() != null) {
    //         songFromDatabase.setArtist(songFromRequest.getArtist());
    //     }
    //     return songFromDatabase;
}