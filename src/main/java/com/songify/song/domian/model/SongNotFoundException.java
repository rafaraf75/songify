package com.songify.song.domian.model;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException(String message) {

        super(message);
    }
}
