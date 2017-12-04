package guru.springframework.netfluxexample.bootstrap;

import guru.springframework.netfluxexample.domain.Movie;
import guru.springframework.netfluxexample.repositories.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component
public class BootstrapCLR  implements CommandLineRunner{


    private final MovieRepository movieRepository;

    public BootstrapCLR(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        movieRepository.deleteAll().thenMany(
                Flux.just("Silence of the Lambs","AEon Flux","Enter the Mono<Void>",
                        "The Fluxxinator","Back to the future","Meet the fluxes","Lord of the Fluxes")
                        .map(title -> new Movie(UUID.randomUUID().toString(),title))
                        .flatMap(movieRepository::save))
                        .subscribe(null,null,()->{
                            movieRepository.findAll().subscribe(System.out::println);
                        });




    }
}
