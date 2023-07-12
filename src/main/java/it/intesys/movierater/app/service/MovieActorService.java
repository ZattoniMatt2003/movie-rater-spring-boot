package it.intesys.movierater.app.service;

import it.intesys.movierater.app.entity.MovieActorEntity;
import it.intesys.movierater.app.entity.MovieEntity;
import it.intesys.movierater.app.repository.MovieActorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MovieActorService {
    private final MovieActorRepository movieActorRepository;

    public MovieActorService(MovieActorRepository movieActorRepository) {
        this.movieActorRepository = movieActorRepository;
    }

    public void insertMovieActor(MovieActorEntity movieActorEntity){
        movieActorRepository.insertMovieActor(movieActorEntity);
    }

    public List<Integer> getMoviesForActor(Integer actorId){
        List<MovieActorEntity> movieActor = movieActorRepository.getMovies(actorId);
        List<Integer> moviesIds = new ArrayList<>();
        for (MovieActorEntity movie: movieActor){
            moviesIds.add(movie.getMovie().getId());
        }
        return moviesIds;
    }

    public List<Integer> getActorsForMovie(Integer movieId){
        List<MovieActorEntity> movieActor = movieActorRepository.getActors(movieId);
        List<Integer> actorsIds = new ArrayList<>();
        for (MovieActorEntity movie: movieActor){
            actorsIds.add(movie.getActor().getId());
        }
        return actorsIds;
    }
}
