
package com.app.multistat.service;

import com.app.multistat.entity.CategorieCarte;
import com.app.multistat.repository.CategorieCarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieCarteService {

    @Autowired
    private CategorieCarteRepository categorieCarteRepository;

    public List<CategorieCarte> obtineCategorii() {
        return categorieCarteRepository.findAll();
    }

    public CategorieCarte adaugaCategorie(CategorieCarte categorie) {
        return categorieCarteRepository.save(categorie);
    }

    public void stergeCategorie(Integer id) {
        categorieCarteRepository.deleteById(id);
    }
}