package com.songify.song.infrastructure.controller.error;

import org.springframework.http.HttpStatus;

public record ErrorDeleteSongResponseDto(String message, HttpStatus status) {
}
