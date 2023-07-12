package it.intesys.movierater.app.repository;
import it.intesys.movierater.app.dto.Movie;
import it.intesys.movierater.app.entity.MovieEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Repository
public class MovieRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final EntityManager em;

    public MovieRepository(NamedParameterJdbcTemplate jdbcTemplate, EntityManager em) {
        this.jdbcTemplate = jdbcTemplate;
        this.em = em;
    }

    public List<MovieEntity> readMovies() {

        List<MovieEntity> movie = em.createQuery("from MovieEntity ", MovieEntity.class)
                .getResultList();

        return movie;
    }

    public MovieEntity getMovieById(Integer movieId){
        return em.find(MovieEntity.class,movieId);

    }

    public List<MovieEntity> getMovieByIds(List<Integer> movieIds){
        List<MovieEntity> movie = em.createQuery("from MovieEntity where id in (:movies)", MovieEntity.class)
                .setParameter("movies",movieIds)
                .getResultList();
        return movie;
    }

    public void updateMovieVote(MovieEntity movie){
        em.merge(movie);
    }
}
