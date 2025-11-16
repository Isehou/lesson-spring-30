package org.example.lessonspring30.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lessonspring30.model.Movie;
import org.example.lessonspring30.service.MovieService;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService service;

    @PostMapping
    public Movie create(@RequestBody Movie movie) {
        return service.addMovie(movie);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return service.getAllMovies();
    }

    @GetMapping("/{id}")
    public Optional<Movie> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        return service.update(id, movie);
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable int id) {
        boolean deleted = service.deleteMovie(id);
        return deleted ? "Фильм помечен как удалён" : "Фильм не найден";
    }

//🔍 Дополнительные эндпоинты

    @GetMapping("/search")
    public List<Movie> searchByTitle(@RequestParam String title) {
        log.info("Файл найден");
        return service.getAllMovies().stream()
                .filter(f -> f.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }


    @GetMapping("/filter")
    public List<Movie> filterMovies(@RequestParam String genre) {
        log.info("Список фильмов по фильтру готов");
        return service.getAllMovies().stream()
                .filter(f -> f.getGenre().toLowerCase().contains(genre))
                .toList();
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        List<Movie> movies = service.getAllMovies();

        long count = movies.size();
        double rating = movies.stream()
                .mapToDouble(Movie::getRating)
                .average()
                .orElse(0.0);

        Map<String, Object> stats = new HashMap<>();
        stats.put("count", count);
        stats.put("averageRating", rating);
        return stats;
    }

//🕹 Контроллер MovieController
//    Метод  URL  Назначение
//    POST  /movies  Добавить фильм
//    GET  /movies  Получить все фильмы
//    GET  /movies/{id}  Получить фильм по ID
//    PUT  /movies/{id}  Обновить фильм
//    DELETE  /movies/{id}  Удалить (soft delete)
//🔍 Дополнительные эндпоинты
//- GET /movies/search?title=Inception — поиск по названию
//- GET /movies/filter?genre=drama — фильтр по жанру
//- GET /movies/filter?from=2000&to=2010 — фильтр по диапазону лет
//- GET /movies/sort?by=rating&order=desc — сортировка по рейтингу или году
//- GET /movies/stats — статистика: количество фильмов и средний рейтинг
//🌐 Примеры запросов (Postman)
//    Операция  Метод  URL  Тело запроса
//    Добавить  POST  /movies  { "title": "Interstellar", "genre": "Sci-Fi", "year": 2014, "rating": 9.0 }
//    Все  GET  /movies  —
//    Поиск  GET  /movies/search?title=star  —
//    Фильтр  GET  /movies/filter?genre=drama  —
//    Обновить  PUT  /movies/1  { "title": "Interstellar", "genre": "Sci-Fi", "year": 2014, "rating": 9.5 }
//    Удалить  DELETE  /movies/1  —
}
