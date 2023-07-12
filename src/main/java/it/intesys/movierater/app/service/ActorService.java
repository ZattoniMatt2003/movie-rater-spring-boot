package it.intesys.movierater.app.service;

import it.intesys.movierater.app.dto.Actor;
import it.intesys.movierater.app.dto.Movie;
import it.intesys.movierater.app.entity.ActorEntity;
import it.intesys.movierater.app.entity.MovieEntity;
import it.intesys.movierater.app.mapper.ActorMapper;
import it.intesys.movierater.app.repository.ActorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ActorService {
    private final ActorRepository actorRepository;

    private final MovieService movieService;
    private final ActorMapper actorMapper;

    private final MovieActorService movieActorService;

    public ActorService(ActorRepository actorRepository, MovieService movieService, ActorMapper actorMapper, MovieActorService movieActorService) {
        this.actorRepository = actorRepository;
        this.movieService = movieService;
        this.actorMapper = actorMapper;
        this.movieActorService = movieActorService;
    }

    public Actor getActorById(Integer actorId){
        return actorMapper.toDTO(actorRepository.getActorById(actorId));
    }

    public List<Actor> getActorsByIds(List<Integer> ids){
        return actorMapper.toDTOList(actorRepository.getActorsByIds(ids));
    }

    public Actor getActorByNameAndSurname(String name, String surname){
        return actorMapper.toDTO(actorRepository.getActorByNameAndSurname(name,surname));
    }

    public List<Actor> getAllActors(){
        return actorMapper.toDTOList(actorRepository.getAllActors());
    }

    public void postActorDTO(Actor actor){
        actorRepository.insertActor(actorMapper.toEntity(actor));
    }

    public void postActorEntity(ActorEntity actorEntity){
        actorRepository.insertActor(actorEntity);
    }

    public Integer getActorVotes(Integer actorId){
        List<Movie> movies = movieService.getMovieByIds(movieActorService.getMoviesForActor(actorId));
        Integer votes = 0;
        for (Movie movie: movies){
            votes+=movie.getVote();
        }
        return votes;
    }

    public List<Integer> top10Actors(HashMap<Integer,Integer> attoriVoti){
        List<Integer> attoriId = new ArrayList<>();
        Integer votiMax = 0;
        Integer attoreSelezionato = 0;
        for(int i= 0;i<10;i++) {
            for (Integer attoreId : attoriVoti.keySet()) {
                if (!attoriId.contains(attoreId) && attoriVoti.get(attoreId) > votiMax) {
                    votiMax = attoriVoti.get(attoreId);
                    attoreSelezionato = attoreId;
                }
            }
            attoriId.add(attoreSelezionato);
            votiMax = 0;
        }
        return attoriId;
    }
}
