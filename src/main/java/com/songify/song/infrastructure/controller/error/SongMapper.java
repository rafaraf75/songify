package com.songify.song.infrastructure.controller.error;

import com.songify.song.domian.model.Song;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongMapper {

    public static SongDto mapFromSongToSongDto(Song song) {
        return new SongDto(song.getId(), song.getName(), song.getArtist());

    }
    public static Song mapFromCreateSongRequestDtoToSongSong(CreateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artist());
    }
    public static Song mapFromUpdateSongRequestDtoToSong(UpdateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artist());
    }
    public static Song mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artist());
    }

    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(Song song) {
        SongDto SongDto = SongMapper.mapFromSongToSongDto(song);
        return new CreateSongResponseDto(SongDto);
    }
    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK);
    }
    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(Song newSong) {
        return new UpdateSongResponseDto(newSong.getName(), newSong.getArtist());
    }
    public static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(Song song) {
        SongDto SongDto = SongMapper.mapFromSongToSongDto(song);
        return new PartiallyUpdateSongResponseDto(SongDto);
    }
    public static GetSongResponseDto mapFromSongToGetSongResponseDto(Song song) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(song);
        return new GetSongResponseDto(songDto);

    }

    public static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(List<Song> songs) {
        List<SongDto> songsDtos = songs.stream()
                .map(SongMapper::mapFromSongToSongDto).toList();
        return new GetAllSongsResponseDto(songsDtos);
    }




}





