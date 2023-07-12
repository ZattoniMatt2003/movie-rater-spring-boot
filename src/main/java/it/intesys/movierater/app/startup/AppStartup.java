package it.intesys.movierater.app.startup;

import it.intesys.movierater.app.dto.Actor;
import it.intesys.movierater.app.dto.Movie;
import it.intesys.movierater.app.service.ActorService;
import it.intesys.movierater.app.service.MovieActorService;
import it.intesys.movierater.app.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;

@Component
public class AppStartup {

    private final Logger log = LoggerFactory.getLogger(AppStartup.class);

    private final MovieService movieService;

    private final ActorService actorService;

    private final MovieActorService movieActorService;

    public AppStartup(MovieService movieService, ActorService actorService, MovieActorService movieActorService) {
        this.movieService = movieService;
        this.actorService = actorService;
        this.movieActorService = movieActorService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void calculateActorsWithLongestCareer() {
        List<Actor> actors = actorService.getAllActors();
        HashMap<Actor,Integer> attoriAnniLavoro = new HashMap<>();
        for (Actor actor:actors){
            List<Integer> anniLavoro = new ArrayList<>();
            List<Integer> ids = movieActorService.getMoviesForActor(actor.getId());
            List<Movie> movies = movieService.getMovieByIds(ids);
            for(Movie movie:movies){
                anniLavoro.add(movie.getYear());
            }
            Collections.sort(anniLavoro);
            Integer anni = anniLavoro.get((int)anniLavoro.stream().count()-1) - anniLavoro.get(0);
            //log.info(String.valueOf(anni));
            attoriAnniLavoro.put(actor,anni);
        }
        List<Actor> attori = getOldestActors(attoriAnniLavoro);
        for(Actor attore: attori){
            log.info(attore.getName()+" "+attore.getSurname() +"->"+attoriAnniLavoro.get(attore));
        }
    }

    public List<Actor> getOldestActors(HashMap<Actor,Integer> attoriAnniLavoro){
        List<Actor> attoriSelezionati = new ArrayList<>();
        Integer anniSelezionato = 0;
        Actor attoreSelezionato = new Actor();
        for(int i= 0;i<3;i++) {
            for (Actor attore : attoriAnniLavoro.keySet()) {
                if (!attoriSelezionati.contains(attore) && attoriAnniLavoro.get(attore) > anniSelezionato) {
                    anniSelezionato = attoriAnniLavoro.get(attore);
                    attoreSelezionato = attore;
                }
            }
            attoriSelezionati.add(attoreSelezionato);
            anniSelezionato = 0;
        }
        return attoriSelezionati;
    }

}
