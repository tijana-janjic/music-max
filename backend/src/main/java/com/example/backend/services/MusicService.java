package com.example.backend.services;

import com.example.backend.dtos.SongBasicDto;
import com.example.backend.mappers.SongMapper;
import com.example.backend.model.music.Author;
import com.example.backend.model.music.Genre;
import com.example.backend.model.music.Singer;
import com.example.backend.model.music.Song;
import com.example.backend.repositories.AuthorRepository;
import com.example.backend.repositories.GenreRepository;
import com.example.backend.repositories.SingerRepository;
import com.example.backend.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MusicService {

    private final GenreRepository genreRepository;
    private final SongRepository songRepository;
    private final SingerRepository singerRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public MusicService(GenreRepository genreRepository, SongRepository songRepository, SingerRepository singerRepository, AuthorRepository authorRepository) {
        this.genreRepository = genreRepository;
        this.songRepository = songRepository;
        this.singerRepository = singerRepository;
        this.authorRepository = authorRepository;
    }
    public List<SongBasicDto> getAllSongsBasic() {
        SongMapper sm = new SongMapper(this);
        return songRepository.findAll()
                .stream().map(sm::toBasicDTO)
                .collect(Collectors.toList());
    }


    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public List<Singer> getAllSingers() {
        return singerRepository.findAll();
    }

    public List<Author> getAllAuthors() { return authorRepository.findAll(); }
    public Song getSong(String name) {
        return songRepository.findByName(name);
    }

    public Genre getGenreById(Long id) {
        return genreRepository.getById(id);
    }

    public List<Singer> getSingersByIds(List<Long> singers) {
        return singerRepository.findAllById(singers);
    }

    public List<Author> getAuthorsByIds(List<Long> authors) {
        return authorRepository.findAllById(authors);
    }

}
