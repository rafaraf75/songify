package com.songify.song.domian.model;

import lombok.Builder;

@Builder
public record Song(String name, String artist) {
}
