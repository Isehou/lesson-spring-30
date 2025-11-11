package org.example.lessonspring30.repository;

import org.example.lessonspring30.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MovieRepository {
    private final Map<Integer, Movie> movies = new HashMap<>();

    public Movie save(Movie movie) {
        movies.put(movie.getId(), movie);
        return movie;
    }

    public List<Movie> findAll() {
        return new ArrayList<>(movies.values());
    }

    public Movie findById(int id) {
        return movies.get(id);
    }

    public Movie update(int id, Movie updated) {
        Movie exist = movies.get(id);
        if(exist != null) {
            exist.setGenre(updated.getGenre());
            exist.setRating(updated.getRating());
            exist.setYear(updated.getYear());
            exist.setTitle(updated.getTitle());
            exist.setDeleted(updated.isDeleted());
        }
        return exist;
    }

    public boolean delete(int id) {
        movies.remove(id);
        return false;
    }

}
