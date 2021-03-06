package com.example.backend.services;

import com.example.backend.dtos.SongBasicDto;
import com.example.backend.mappers.SongMapper;
import com.example.backend.model.music.*;
import com.example.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicService {

    private final GenreRepository genreRepository;
    private final SongRepository songRepository;
    private final SingerRepository singerRepository;
    private final AuthorRepository authorRepository;
    private final RateRepository rateRepository;

    @Autowired
    public MusicService(GenreRepository genreRepository, SongRepository songRepository, SingerRepository singerRepository, AuthorRepository authorRepository, RateRepository rateRepository) {
        this.genreRepository = genreRepository;
        this.songRepository = songRepository;
        this.singerRepository = singerRepository;
        this.authorRepository = authorRepository;
        this.rateRepository = rateRepository;
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

    public void addSong(Song song) {
        songRepository.save(song);
    }

    public List<SongBasicDto> getTopList() {
        List<Song> songs = songRepository.findAll();
        songs.sort(
                (x, y) -> {
                    if(getAvgRate(x) > getAvgRate(y))
                        return -1;
                    else if(getAvgRate(x) > getAvgRate(y))
                        return 1;
                    else
                        return 0;
                });
        SongMapper sm = new SongMapper(this);
        return songs.stream().map(sm::toBasicDTO)
                .collect(Collectors.toList());
    }

    private double getAvgRate(Song song) {
        List<Rate> list = song.getRates();
        if(list.size() == 0)
            return 0;
        double sum = 0;
        for (Rate rate: list ) {
            sum += rate.getRate();
        }
        System.err.println("ocena " + sum / list.size());
        return sum / list.size();
    }

    public Long getDiligentUserId() {
        return songRepository.getDiligent();
    }

    public void addRate(Rate rate) {
        rateRepository.save(rate);
    }
}
