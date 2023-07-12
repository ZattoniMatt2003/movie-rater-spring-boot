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

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
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

    public Movie getMovieById(Integer movieId){
        return movieMapper.toDTO(movieRepository.getMovieById(movieId));
    }
    public List<Movie> getMovieByIds(List<Integer> movieId){
        return movieMapper.toDTOList(movieRepository.getMovieByIds(movieId));
    }

    public Long getMovieCount() {
        return getAllMovies().stream().count();
    }

    public List<Movie> getAllMovies(){
        return movieMapper.toDTOList(movieRepository.readMovies());
    }

    public void vote(Long movieId) {
        MovieEntity movie = movieRepository.getMovieById((movieId).intValue());
        Integer voto = getVoteById((movieId).intValue())+1;
        movie.setVote(voto);
        movieRepository.updateMovieVote(movie);
        logger.info(String.valueOf(voto));
    }

    public Integer getVoteById(Integer movieId){
        return movieRepository.getMovieById(movieId).getVote();
    }

    public Long getVotesCount(){
        List<MovieEntity> movies = movieRepository.readMovies();
        Integer result = 0;
        for (MovieEntity movie: movies) {
            result += movie.getVote();
        }
        return (long)result;
    }
}
