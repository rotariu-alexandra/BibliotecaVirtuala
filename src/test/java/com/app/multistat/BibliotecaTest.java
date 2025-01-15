package com.app.multistat;

import com.app.multistat.entity.*;
import com.app.multistat.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BibliotecaTest {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private ImprumutRepository imprumutRepository;

    @Autowired
    private IstoricActivitateUtilizatorRepository istoricRepository;

    @Test
    public void testCreareImprumutSiIstoric() {
        // Cream un utilizator
        Utilizator utilizator = new Utilizator();
        utilizator.setNume("Popescu");
        utilizator.setPrenume("Ion");
        utilizator.setEmail("ion.popescu@example.com");
        utilizator.setRol("utilizator");
        utilizator = utilizatorRepository.save(utilizator);

        // Cream o carte
        Carte carte = new Carte();
        carte.setTitlu("TitluCarte");
        carte = carteRepository.save(carte);

        //  Cream un împrumut
        Imprumut imprumut = new Imprumut();
        imprumut.setUtilizator(utilizator);
        imprumut.setCarte(carte);
        imprumut.setDataImprumut(new Date());
        imprumut.setDataReturnare(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L)); // 7 zile
        imprumut = imprumutRepository.save(imprumut);

        // Verificam daca imprumutul a fost salvat.
        List<Imprumut> imprumuturiUtilizator = imprumutRepository.findByUtilizator_Id(utilizator.getId());
        assertThat(imprumuturiUtilizator).hasSize(1);
        assertThat(imprumuturiUtilizator.get(0).getCarte().getTitlu()).isEqualTo("TitluCarte");

        // Adaugam o inregistrare in istoricul utilizatorului.
        IstoricActivitateUtilizator istoric = new IstoricActivitateUtilizator();
        istoric.setUtilizator(utilizator);
        istoric.setActivitate("Împrumutat cartea: " + carte.getTitlu());
        istoric.setDataActivitate(new Date());
        istoricRepository.save(istoric);

        // Verificam istoricul utilizatorului.
        List<IstoricActivitateUtilizator> istoricUtilizator = istoricRepository.findByUtilizator_Id(utilizator.getId());
        assertThat(istoricUtilizator).hasSize(1);
        assertThat(istoricUtilizator.get(0).getActivitate()).contains("Împrumutat cartea: TitluCarte");
    }
}



