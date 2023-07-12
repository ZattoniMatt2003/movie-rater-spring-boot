package it.intesys.movierater.app.startup;

import it.intesys.movierater.app.dto.Actor;
import it.intesys.movierater.app.dto.Movie;
import it.intesys.movierater.app.entity.ActorEntity;
import it.intesys.movierater.app.entity.MovieActorEntity;
import it.intesys.movierater.app.mapper.ActorMapper;
import it.intesys.movierater.app.mapper.MovieMapper;
import it.intesys.movierater.app.repository.ActorRepository;
import it.intesys.movierater.app.repository.MovieActorRepository;
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

@Component
public class ActorStartup {

    private final Logger log = LoggerFactory.getLogger(ActorStartup.class);
    private final ActorService actorService;

    private final MovieService movieService;

    private final MovieMapper movieMapper;

    private final ActorMapper actorMapper;

    private final MovieActorService movieActorService;


    public ActorStartup(ActorService actorService, MovieService movieService, MovieMapper movieMapper, ActorMapper actorMapper,MovieActorService movieActorService ) {
        this.actorService = actorService;
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.actorMapper = actorMapper;
        this.movieActorService = movieActorService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupTableActors() {
        //controllo della tabella
        if(actorService.getAllActors().isEmpty()){
            //lista vuota, procedo a prendere gli attori
            HashMap<String, List<Integer>> attori = getAttoriPlusIdFilm(movieService.getAllMovies());
            //Procedo a inserirli
            for (String attore: attori.keySet()){
                log.info(attore);
                ActorEntity attoreDaInserire = prepareActor(attore);
                actorService.postActorEntity(attoreDaInserire);
                for(Integer id: attori.get(attore)){
                    MovieActorEntity movieActor = new MovieActorEntity();
                    movieActor.setMovie(movieMapper.toEntity(movieService.getMovieById(id)));
                    movieActor.setActor(actorMapper.toEntity(actorService.getActorByNameAndSurname(attoreDaInserire.getName(),attoreDaInserire.getSurname())));
                    movieActorService.insertMovieActor(movieActor);
                }
            }

        }else{
            log.info("Attori già creati");
        }
    }

    public HashMap<String, List<Integer>> getAttoriPlusIdFilm(List<Movie> movies){
        //mappa attore, lista i id dei film a cui a partecipato
        HashMap<String, List<Integer>> attoriAnniFilm = new HashMap<>();
        //per ogni film
        for (Movie movie: movies) {
            //divido gli attori dato che sono contenuti in una stringa divisa da ", "
            String[] attoriMovie = movie.getActors().split(", ");

            //Per ogni attore
            for (String attore: attoriMovie) {

                //Vedo se è gia presente o no
                if(attoriAnniFilm.containsKey(attore)){
                    //se si gli aggiungo solo l'id del film
                    List<Integer> anni = attoriAnniFilm.get(attore);
                    anni.add(movie.getId().intValue());
                    attoriAnniFilm.replace(attore,anni);
                }else{
                    //alrimenti lo aggiungo al dizionario
                    List<Integer> anni = new ArrayList<>();
                    anni.add(movie.getId().intValue());
                    attoriAnniFilm.put(attore,anni);
                }
            }
        }
        return attoriAnniFilm;
    }

    public ActorEntity prepareActor(String attore){
        String[] namesurname = attore.split(" ");
        ActorEntity attoreDaInserire = new ActorEntity();
        String surname ="";
        attoreDaInserire.setName(namesurname[0]);
        namesurname[0] = "";
        for(String surnames :namesurname){
            surname += surnames+" ";
        }
        attoreDaInserire.setSurname(surname);
        return attoreDaInserire;
    }
}
