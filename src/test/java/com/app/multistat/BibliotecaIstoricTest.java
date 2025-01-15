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
public class BibliotecaIstoricTest {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private IstoricActivitateUtilizatorRepository istoricRepository;

    @Autowired
    private ImprumutRepository imprumutRepository;

    @Test
    public void testGestionareIstoricUtilizator() {
        // Cream un utilizator
        Utilizator utilizator = new Utilizator();
        utilizator.setNume("Marin");
        utilizator.setPrenume("George");
        utilizator.setEmail("george.marin@example.com");
        utilizator.setRol("utilizator");
        utilizator = utilizatorRepository.save(utilizator);

        // Cream doua carti.
        Carte carte1 = new Carte();
        carte1.setTitlu("Design Patterns în Java");
        carte1 = carteRepository.save(carte1);

        Carte carte2 = new Carte();
        carte2.setTitlu("Algoritmi și structuri de date");
        carte2 = carteRepository.save(carte2);

        // Utilizatorul imprumuta prima carte.
        Imprumut imprumut1 = new Imprumut();
        imprumut1.setUtilizator(utilizator);
        imprumut1.setCarte(carte1);
        imprumut1.setDataImprumut(new Date());
        imprumut1.setDataReturnare(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L)); // 7 zile
        imprumut1 = imprumutRepository.save(imprumut1);

        // Adaugam actiunea in istoric.
        IstoricActivitateUtilizator istoric1 = new IstoricActivitateUtilizator();
        istoric1.setUtilizator(utilizator);
        istoric1.setActivitate("Împrumutat cartea: " + carte1.getTitlu());
        istoric1.setDataActivitate(new Date());
        istoricRepository.save(istoric1);

        // Utilizatorul imprumuta a doua carte.
        Imprumut imprumut2 = new Imprumut();
        imprumut2.setUtilizator(utilizator);
        imprumut2.setCarte(carte2);
        imprumut2.setDataImprumut(new Date());
        imprumut2.setDataReturnare(new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000L)); // 14 zile
        imprumut2 = imprumutRepository.save(imprumut2);

        // Adaugam actiunea in istoric.
        IstoricActivitateUtilizator istoric2 = new IstoricActivitateUtilizator();
        istoric2.setUtilizator(utilizator);
        istoric2.setActivitate("Împrumutat cartea: " + carte2.getTitlu());
        istoric2.setDataActivitate(new Date());
        istoricRepository.save(istoric2);

        // Verificam istoricul utilizatorului.
        List<IstoricActivitateUtilizator> istoricUtilizator = istoricRepository.findByUtilizator_Id(utilizator.getId());
        assertThat(istoricUtilizator).hasSize(2);
        assertThat(istoricUtilizator.get(0).getActivitate()).contains("Împrumutat cartea: TitluCarte3");
        assertThat(istoricUtilizator.get(1).getActivitate()).contains("Împrumutat cartea: TitluCarte4");

        // Verificam daca imprumuturile sunt corect asociate utilizatorului.
        List<Imprumut> imprumuturiUtilizator = imprumutRepository.findByUtilizator_Id(utilizator.getId());
        assertThat(imprumuturiUtilizator).hasSize(2);
        assertThat(imprumuturiUtilizator.get(0).getCarte().getTitlu()).isEqualTo("TitluCarte3");
        assertThat(imprumuturiUtilizator.get(1).getCarte().getTitlu()).isEqualTo("TitluCarte4");
    }
}