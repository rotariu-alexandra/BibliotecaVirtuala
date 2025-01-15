package com.app.multistat.service;

import com.app.multistat.entity.Utilizator;
import com.app.multistat.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilizatorService {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    public List<Utilizator> obtineUtilizatori() {
        return utilizatorRepository.findAll();
    }

    public Utilizator obtineUtilizator(Integer id) {
        return utilizatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit!"));
    }

    public Utilizator editeazaUtilizator(Integer id, Utilizator utilizatorActualizat) {
        Utilizator utilizator = obtineUtilizator(id);
        utilizator.setNume(utilizatorActualizat.getNume());
        utilizator.setEmail(utilizatorActualizat.getEmail());

        return utilizatorRepository.save(utilizator);
    }

    public void stergeUtilizator(Integer id) {
        utilizatorRepository.deleteById(id);
    }
}