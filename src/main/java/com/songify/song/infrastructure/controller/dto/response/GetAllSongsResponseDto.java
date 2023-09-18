package com.songify.song.infrastructure.controller.dto.response;

import com.songify.song.domian.model.Song;

import java.util.List;

public record GetAllSongsResponseDto(List<Song> songs) {
}
