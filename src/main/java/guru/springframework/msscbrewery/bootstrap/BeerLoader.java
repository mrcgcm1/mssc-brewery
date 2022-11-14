package guru.springframework.msscbrewery.bootstrap;

import guru.springframework.msscbrewery.domain.Beer;
import guru.springframework.msscbrewery.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if (beerRepository.count() == 0) {
            beerRepository.save(Beer.builder().beerName("Mia birra").beerStyle("Mio stile").quantityToBrew(200).minOnHand(12).upc(12L).price(new BigDecimal("12.95")).build());
            beerRepository.save(Beer.builder().beerName("Tua birra").beerStyle("Tuo stile").quantityToBrew(400).minOnHand(2).upc(14L).price(new BigDecimal("15.95")).build());
            beerRepository.save(Beer.builder().beerName("Sua birra").beerStyle("Suo stile").quantityToBrew(600).minOnHand(24).upc(16L).price(new BigDecimal("18.95")).build());
        }

        System.out.println("Birre caricate " + beerRepository.count());
    }
}
