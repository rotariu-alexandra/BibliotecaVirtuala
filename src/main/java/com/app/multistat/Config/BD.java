package com.app.multistat.Config;

import com.app.multistat.entity.*;
import com.app.multistat.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BD {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private CategorieCarteRepository categorieCarteRepository;

    @Autowired
    private DetaliiCarteRepository detaliiCarteRepository;

    @Autowired
    private ImprumutRepository imprumutRepository;

    @Bean
    public CommandLineRunner seedDatabase() {
        return args -> {
            // Inserăm câteva categorii
            if (categorieCarteRepository.count() == 0) {
                CategorieCarte categorie1 = new CategorieCarte();
                categorie1.setName("Fictiune");
                categorieCarteRepository.save(categorie1);

                CategorieCarte categorie2 = new CategorieCarte();
                categorie2.setName("Istorie");
                categorieCarteRepository.save(categorie2);
                System.out.println("2 Categorie inserate cu succes!");
            }

            // Inserăm câteva detalii pentru cărți
            if (detaliiCarteRepository.count() == 0) {
                DetaliiCarte detalii1 = new DetaliiCarte();
                detalii1.setStatusCarte("Disponibila");
                detalii1.setDescriereCarte("Cartea 1 în stare bună.");
                detaliiCarteRepository.save(detalii1);

                DetaliiCarte detalii2 = new DetaliiCarte();
                detalii2.setStatusCarte("Imprumutata");
                detalii2.setDescriereCarte("Cartea 2 împrumutată.");
                detaliiCarteRepository.save(detalii2);

                System.out.println("2 DetaliiCarte inserate cu succes!");
            }

            // Inserăm câțiva utilizatori
            if (utilizatorRepository.count() == 0) {
                Utilizator utilizator1 = new Utilizator();
                utilizator1.setNume("Ion");
                utilizator1.setPrenume("Popescu");
                utilizator1.setEmail("ion.popescu@example.com");
                utilizator1.setRol("admin");

                Utilizator utilizator2 = new Utilizator();
                utilizator2.setNume("Maria");
                utilizator2.setPrenume("Ionescu");
                utilizator2.setEmail("maria.ionescu@example.com");
                utilizator2.setRol("utilizator");

                utilizatorRepository.save(utilizator1);
                utilizatorRepository.save(utilizator2);

                System.out.println("2 Utilizatori inserati cu succes!");
            }

            // Inserăm câteva cărți
            if (carteRepository.count() == 0) {
                Carte carte1 = new Carte();
                carte1.setTitlu("Cartea Exemplu 1");
                carte1.setDomeniu("Fictiune");
                carte1.setCategorie(categorieCarteRepository.findById(1).orElse(null));
                carte1.setDetaliiCarte(detaliiCarteRepository.findById(1).orElse(null));
                carteRepository.save(carte1);

                Carte carte2 = new Carte();
                carte2.setTitlu("Cartea Exemplu 2");
                carte2.setDomeniu("Istorie");
                carte2.setCategorie(categorieCarteRepository.findById(2).orElse(null));
                carte2.setDetaliiCarte(detaliiCarteRepository.findById(2).orElse(null));
                carteRepository.save(carte2);

                System.out.println("2 Cărți inserate cu succes!");
            }

            // Inserăm câteva împrumuturi
            if (imprumutRepository.count() == 0) {
                // Obține un utilizator și o carte existentă
                Utilizator utilizator = utilizatorRepository.findById(1).orElse(null);
                Carte carte = carteRepository.findById(1).orElse(null);

                if (utilizator != null && carte != null) {
                    Imprumut imprumut = new Imprumut();
                    imprumut.setUtilizator(utilizator);
                    imprumut.setCarte(carte);
                    imprumut.setDataImprumut(java.sql.Date.valueOf(java.time.LocalDate.now()));
                    imprumut.setDataReturnare(java.sql.Date.valueOf(java.time.LocalDate.now().plusWeeks(2)));
                    imprumut.setReturnat(false);
                    imprumutRepository.save(imprumut);
                    System.out.println("1 Împrumut inserat cu succes!");
                }
            }
        };
    }
}
