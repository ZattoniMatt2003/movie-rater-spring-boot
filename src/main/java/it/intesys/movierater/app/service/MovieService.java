package it.intesys.movierater.app.service;

import java.util.Random;
import it.intesys.movierater.app.mapper.MovieMapper;
import it.intesys.movierater.app.dto.Movie;
import it.intesys.movierater.app.entity.MovieEntity;
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

    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public Pair<Movie, Movie> get2RandomMovies() {
        Integer movieCount = getMovieCount().intValue();
        Random random = new Random();
        return Pair.with(
                movieMapper.toDTO(movieRepository.getMovieById(random.nextInt(movieCount))),
                movieMapper.toDTO(movieRepository.getMovieById(random.nextInt(movieCount))));
    }

    public Long getMovieCount() {
        return getAllMovies().stream().count();
    }

    public List<Movie> getAllMovies(){
        return movieMapper.toDTOList(movieRepository.readMovies());
    }

    public void vote(Long movieId) {
        logger.info("Add vote for movie {}", movieId);
    }
}
