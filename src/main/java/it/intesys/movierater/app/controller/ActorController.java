package it.intesys.movierater.app.controller;

import it.intesys.movierater.app.dto.Movie;
import it.intesys.movierater.app.service.ActorService;
import it.intesys.movierater.app.service.MovieActorService;
import it.intesys.movierater.app.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class ActorController {

    private final ActorService actorService;

    private final MovieService movieService;

    private final MovieActorService movieActorService;

    public ActorController(ActorService actorService, MovieService movieService, MovieActorService movieActorService) {
        this.actorService = actorService;
        this.movieService = movieService;
        this.movieActorService = movieActorService;
    }

    @GetMapping("/actor/{actorId}")
    public String getMovieDetails(Model model, @PathVariable("actorId") Long actorId) {
        model.addAttribute("actor", actorService.getActorById((actorId).intValue()));
        List<Movie> movies = movieService.getMovieByIds(movieActorService.getMoviesForActor((actorId).intValue()));
        model.addAttribute("movies",movies);
        if(actorService.top10Actors(movieService.getActorsVotes(actorService.getAllActors())).contains((actorId).intValue())) {
            model.addAttribute("votes","TOP 10");
        }else{
            model.addAttribute("votes","");
        }
        return "actor";
    }
}
