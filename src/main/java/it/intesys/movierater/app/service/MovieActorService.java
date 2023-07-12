package it.intesys.movierater.app.service;

import it.intesys.movierater.app.entity.MovieActorEntity;
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

    public List<Integer> getMovieForActor(Integer actorId){
        return new ArrayList<>();
    }
}
