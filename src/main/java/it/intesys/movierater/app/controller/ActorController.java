package it.intesys.movierater.app.controller;

import it.intesys.movierater.app.service.ActorService;
import it.intesys.movierater.app.service.MovieActorService;
import it.intesys.movierater.app.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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
        model.addAttribute("movies",movieService.getMovieByIds(movieActorService.getMoviesForActor((actorId).intValue())));
        return "actor";
    }
}
