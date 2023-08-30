package com.songify.song.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateSongRequestDto(

        @NotNull(message = "songName name cannot be null")
        @NotEmpty(message = "songName name cannot be empty")
        String songName,

        @NotNull(message = "Artist name cannot be null")
        @NotEmpty(message = "Artist name cannot be empty")
        String artist) {
}
