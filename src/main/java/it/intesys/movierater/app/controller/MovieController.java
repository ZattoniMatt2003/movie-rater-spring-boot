package it.intesys.movierater.app.controller;

import it.intesys.movierater.app.dto.Movie;
import it.intesys.movierater.app.service.MovieService;
import org.javatuples.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String index(Model model) {
        Pair<Movie, Movie> randomMovies = movieService.get2RandomMovies();
        model.addAttribute("movie1", randomMovies.getValue0());
        model.addAttribute("movie2", randomMovies.getValue1());
        return "index";
    }

    @ModelAttribute(name="movieCount")
    public Long movieCount() {
        return movieService.getMovieCount();
    }

    @ModelAttribute(name="voteCount")
    public Long voteCount() {
        return movieService.getVotesCount();
    }

    @PostMapping("/vote")
    public String submitVote(@ModelAttribute Movie movie) {
        movieService.vote(movie.getId());
        return "redirect:/";
    }

    @GetMapping("/movie/{movieId}")
    public String getMovieDetails(@PathVariable("movieId") Long movieId) {
        return "movie";
    }
}
