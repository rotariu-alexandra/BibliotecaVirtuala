

package com.app.multistat.service;

import com.app.multistat.entity.Carte;
import com.app.multistat.repository.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarteService {

    @Autowired
    private CarteRepository carteRepository;

    public List<Carte> obtineCarti() {
        return carteRepository.findAll();
    }

    public Carte adaugaCarte(Carte carte) {
        return carteRepository.save(carte);
    }

    public void stergeCarte(Integer id) {
        carteRepository.deleteById(id);
    }

    public List<Carte> cautaCarti(String criteriu) {
        // Exemplu simplu: cautare dupa titlu
        return carteRepository.findByTitluContaining(criteriu);
    }

    public List<Carte> filtreazaCarti(String domeniu) {
        // Exemplu: filtrare dupa domeniu
        return carteRepository.findByDomeniu(domeniu);
    }
}