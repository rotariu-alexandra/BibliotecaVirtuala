package com.app.multistat;

import com.app.multistat.entity.*;
import com.app.multistat.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BibliotecaRecenziiTest {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private RecenzieCarteRepository recenzieCarteRepository;

    @Test
    public void testAdaugareRecenzie() {
        // Cream un utilizator
        Utilizator utilizator = new Utilizator();
        utilizator.setNume("Ionescu");
        utilizator.setPrenume("Maria");
        utilizator.setEmail("maria.ionescu@example.com");
        utilizator.setRol("utilizator");
        utilizator = utilizatorRepository.save(utilizator);

        // Cream o carte
        Carte carte = new Carte();
        carte.setTitlu("TitluCarte2");
        carte = carteRepository.save(carte);

        // Cream o recenzie pentru carte
        RecenzieCarte recenzie = new RecenzieCarte();
        recenzie.setCarte(carte);
        recenzie.setUtilizator(utilizator);
        recenzie.setRating(5); // Rating de 5 stele
        recenzie.setComentariu("Cartea este buna.");
        recenzie = recenzieCarteRepository.save(recenzie);

        // Verificam daca recenzia a fost salvata corect.
        List<RecenzieCarte> recenziiCarte = recenzieCarteRepository.findByCarte_Id(carte.getId());
        assertThat(recenziiCarte).hasSize(1);
        assertThat(recenziiCarte.get(0).getComentariu()).isEqualTo("Cartea este buna.");
        assertThat(recenziiCarte.get(0).getRating()).isEqualTo(5);

        // Verificam daca recenzia este asociata corect cu utilizatorul.
        List<RecenzieCarte> recenziiUtilizator = recenzieCarteRepository.findByUtilizator_Id(utilizator.getId());
        assertThat(recenziiUtilizator).hasSize(1);
        assertThat(recenziiUtilizator.get(0).getCarte().getTitlu()).isEqualTo("TitluCarte2");
    }
}