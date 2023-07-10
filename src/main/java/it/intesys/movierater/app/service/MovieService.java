package it.intesys.movierater.app.service;

import it.intesys.movierater.app.dto.Movie;
import it.intesys.movierater.app.repository.MovieRepository;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Pair<Movie, Movie> get2RandomMovies() {
        return Pair.with(
                new Movie(1L,"Pulp Fiction", "Quentin Tarantino"),
                new Movie(2L, "Titanic", "James Cameron"));
    }

    public Long getMovieCount() {
        return getAllMovies().stream().count();
    }

    public List<Movie> getAllMovies(){
        return movieRepository.readMovies();
    }

    public void vote(Long movieId) {
        logger.info("Add vote for movie {}", movieId);
    }
}
