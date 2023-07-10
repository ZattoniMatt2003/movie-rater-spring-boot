package it.intesys.movierater.app.startup;

import it.intesys.movierater.app.dto.Movie;
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

    public AppStartup(MovieService movieService) {
        this.movieService = movieService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void calculateActorsWithLongestCareer() {
        List<Movie> movies = movieService.getAllMovies();
        HashMap<String, List<Integer>> attoriAnniFilm = getAttoriAnniFilm(movies);
        HashMap<String,Integer> attoriAnniLavoro = getAttoriAnniLavoro(attoriAnniFilm);
        List<String> attori = getOldestActors(attoriAnniLavoro);
        for(String attore: attori){
            log.info(attore +"->"+attoriAnniLavoro.get(attore));
        }

    }

    /**
     * Prende gli attori e gli anni dei film a cui hanno lavorato
     * @param movies
     * @return
     */
    public HashMap<String, List<Integer>> getAttoriAnniFilm(List<Movie> movies){
        HashMap<String, List<Integer>> attoriAnniFilm = new HashMap<>();
        for (Movie movie: movies) {
            String[] attoriMovie = movie.getActors().split(", ");
            for (String attore: attoriMovie) {
                if(attoriAnniFilm.containsKey(attore)){
                    List<Integer> anni = attoriAnniFilm.get(attore);
                    anni.add(movie.getYear());
                    attoriAnniFilm.replace(attore,anni);
                }else{
                    List<Integer> anni = new ArrayList<>();
                    anni.add(movie.getYear());
                    attoriAnniFilm.put(attore,anni);
                }
            }
        }
        return attoriAnniFilm;
    }

    /**
     * Prende gli attori e calcola gli anni di servizio
     * @param attoriAnniFilm
     * @return
     */
    public HashMap<String,Integer> getAttoriAnniLavoro(HashMap<String, List<Integer>> attoriAnniFilm){
        HashMap<String,Integer> attoriAnniLavoro = new HashMap<>();
        for (String attore: attoriAnniFilm.keySet()) {
            List<Integer> anniLavoro = attoriAnniFilm.get(attore);
            Collections.sort(anniLavoro);
            Integer anni = anniLavoro.get((int)anniLavoro.stream().count()-1) - anniLavoro.get(0);
            attoriAnniLavoro.put(attore,anni);
        }
        return attoriAnniLavoro;
    }

    /**
     *
     */
    public List<String> getOldestActors(HashMap<String,Integer> attoriAnniLavoro){
        List<String> attoriSelezionati = new ArrayList<>();
        Integer anniSelezionato = 0;
        String attoreSelezionato = "";
        for(int i= 0;i<3;i++) {
            for (String attore : attoriAnniLavoro.keySet()) {
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
