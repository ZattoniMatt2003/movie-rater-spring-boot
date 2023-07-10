package it.intesys.movierater.app.repository;
import it.intesys.movierater.app.dto.Movie;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MovieRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Movie> readMovies() {

        List<Movie> movie = jdbcTemplate.query("SELECT * FROM movie",
                BeanPropertyRowMapper.newInstance(Movie.class));

        return movie;
    }
}
