package com.songify.song.infrastructure.controller.dto.response;

import com.songify.song.domian.model.Song;

public record PartiallyUpdateSongResponseDto(Song updatedSong) {
}
