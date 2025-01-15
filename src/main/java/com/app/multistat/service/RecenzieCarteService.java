

package com.app.multistat.service;

import com.app.multistat.entity.RecenzieCarte;
import com.app.multistat.repository.RecenzieCarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecenzieCarteService {

    @Autowired
    private RecenzieCarteRepository recenzieCarteRepository;

    public List<RecenzieCarte> obtineRecenzii() {
        return recenzieCarteRepository.findAll();
    }

    public RecenzieCarte adaugaRecenzie(RecenzieCarte recenzie) {
        return recenzieCarteRepository.save(recenzie);
    }

    public void stergeRecenzie(Integer id) {
        recenzieCarteRepository.deleteById(id);
    }
}