package it.intesys.movierater.app.mapper;

import it.intesys.movierater.app.dto.Movie;
import it.intesys.movierater.app.entity.MovieEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieMapper {
    public Movie toDTO(MovieEntity movieEntity){
        Movie movie = new Movie();
        movie.setId(movieEntity.getId().longValue());
        movie.setTitle(movieEntity.getTitle());
        movie.setYear(movieEntity.getYear());
        movie.setGenre(movieEntity.getGenre());
        movie.setDirector(movieEntity.getDirector());
        movie.setActors(movieEntity.getActors());
        movie.setCountry(movieEntity.getCountry());
        movie.setVote(movieEntity.getVote());
        return movie;
    }

    public MovieEntity toEntity(Movie movie){
        MovieEntity movieEntity= new MovieEntity();
        movieEntity.setId(movie.getId().intValue());
        movieEntity.setTitle(movie.getTitle());
        movieEntity.setYear(movie.getYear());
        movieEntity.setGenre(movie.getGenre());
        movieEntity.setDirector(movie.getDirector());
        movieEntity.setActors(movie.getActors());
        movieEntity.setCountry(movie.getCountry());
        movieEntity.setVote(movie.getVote());
        return movieEntity;
    }

    public List<MovieEntity> toEntityList(List<Movie> moviesDTO){
        List<MovieEntity> movies = new ArrayList<>();
        for (Movie movie: moviesDTO
        ) {
            movies.add(toEntity(movie));
        }
        return movies;
    }

    public List<Movie> toDTOList(List<MovieEntity> moviesEntity){
        List<Movie> movies = new ArrayList<>();
        for (MovieEntity movie: moviesEntity
        ) {
            movies.add(toDTO(movie));
        }
        return movies;
    }
}
