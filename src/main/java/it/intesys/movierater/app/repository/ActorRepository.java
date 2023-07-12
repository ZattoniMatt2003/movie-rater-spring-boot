package it.intesys.movierater.app.repository;

import it.intesys.movierater.app.entity.ActorEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ActorRepository {
    private final EntityManager em;

    public ActorRepository(EntityManager em) {
        this.em = em;
    }

    public ActorEntity getActorById(Integer actorId){
        return em.createQuery("FROM ActorEntity WHERE id=:actorId",ActorEntity.class)
                .setParameter("actorId",actorId)
                .getSingleResult();
    }

    public ActorEntity getActorByNameAndSurname(String name, String surname){
        return em.createQuery("FROM ActorEntity WHERE name=:name and surname=:surname",ActorEntity.class)
                .setParameter("name",name)
                .setParameter("surname",surname)
                .getSingleResult();
    }

    public List<ActorEntity> getAllActors(){
        return em.createQuery("FROM ActorEntity",ActorEntity.class)
                .getResultList();
    }

    public void insertActor(ActorEntity actor){
        em.persist(actor);
        em.flush();
    }


}
