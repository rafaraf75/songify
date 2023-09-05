package com.songify.song.domian.service;

import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.CreateSongResponseDto;
import com.songify.song.domian.model.Song;

public class SongMapper {
    public static Song mapFromCreateSongRequestDtoToSongSong(CreateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artist());
    } public static CreateSongResponseDto mapFromSongCreateSongResponseDto(Song song) {
        return new CreateSongResponseDto(song);
    }
}
