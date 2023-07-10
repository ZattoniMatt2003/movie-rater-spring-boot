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
        movie.setDirector(movieEntity.getDirector());
        return movie;
    }

    public MovieEntity toEntity(Movie movie){
        MovieEntity movieEntity= new MovieEntity();
        movieEntity.setId(movie.getId().intValue());
        movieEntity.setTitle(movie.getTitle());
        movieEntity.setDirector(movie.getDirector());
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
