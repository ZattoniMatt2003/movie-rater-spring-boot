package it.intesys.movierater.app.service;

import it.intesys.movierater.app.dto.Actor;
import it.intesys.movierater.app.entity.ActorEntity;
import it.intesys.movierater.app.mapper.ActorMapper;
import it.intesys.movierater.app.repository.ActorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ActorService {
    private final ActorRepository actorRepository;

    private final ActorMapper actorMapper;

    public ActorService(ActorRepository actorRepository, ActorMapper actorMapper) {
        this.actorRepository = actorRepository;
        this.actorMapper = actorMapper;
    }

    public Actor getActorById(Integer actorId){
        return actorMapper.toDTO(actorRepository.getActorById(actorId));
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
}
