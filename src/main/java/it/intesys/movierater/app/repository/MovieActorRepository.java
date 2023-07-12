package it.intesys.movierater.app.repository;

import it.intesys.movierater.app.entity.MovieActorEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class MovieActorRepository {
    private final EntityManager em;

    public MovieActorRepository(EntityManager em) {
        this.em = em;
    }

    public List<MovieActorEntity> getActors(Integer actorId){
        return em.createQuery("FROM MovieActorEntity where actor.id = :actorId", MovieActorEntity.class)
                .setParameter("actorId",actorId)
                .getResultList();
    }

    public List<MovieActorEntity> getMovies(Integer movieId){
        return em.createQuery("FROM MovieActorEntity where movie.id = :movieId", MovieActorEntity.class)
                .setParameter("movieId",movieId)
                .getResultList();
    }

    public void insertMovieActor(MovieActorEntity movieActorEntity){
        em.persist(movieActorEntity);
        em.flush();
    }
}
