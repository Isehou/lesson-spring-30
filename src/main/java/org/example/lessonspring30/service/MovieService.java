package org.example.lessonspring30.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lessonspring30.model.Movie;
import org.example.lessonspring30.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepository moviesrepository;

    public Movie addMovie(Movie movie) {
        validate(movie);
        return moviesrepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return moviesrepository.findAll()
                .stream()
                .filter(f -> !f.isDeleted())
                .toList();
    }

    public Optional<Movie> getById(int id) {
        return Optional.ofNullable(moviesrepository.findById(id));
    }

    public Movie update(int id, Movie movie) {
        validate(movie);
        Movie existing = moviesrepository.update(id, movie);
        return existing;
    }

    public boolean deleteMovie(int id) {
        return moviesrepository.delete(id);
    }

    public void validate(Movie movie) {
        if(movie.getGenre() == null || movie.getTitle() == null) {
            throw new NullPointerException("Поле не может быть пустым");
        }
        if(movie.getYear() < 1900 || movie.getYear() > 2025) {
            throw new IllegalArgumentException("Некорректный год фильма: " + movie.getYear());
        }
        if(movie.getRating() < 0.0 || movie.getRating() > 10.0) {
            throw new IllegalArgumentException("Рейтинг должен быть в диапазоне от 0.0 до 10.0");
        }
    }

}
