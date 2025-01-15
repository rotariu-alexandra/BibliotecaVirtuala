package com.app.multistat.controller;

import com.app.multistat.entity.CategorieCarte;
import com.app.multistat.service.CategorieCarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorii")
public class CategorieCarteController {

    @Autowired
    private CategorieCarteService categorieCarteService;

    @GetMapping
    public ResponseEntity<List<CategorieCarte>> getCategorii() {
        List<CategorieCarte> categorii = categorieCarteService.obtineCategorii();
        return ResponseEntity.ok(categorii);
    }

    @PostMapping
    public ResponseEntity<CategorieCarte> adaugaCategorie(@RequestBody CategorieCarte categorie) {
        CategorieCarte categorieNoua = categorieCarteService.adaugaCategorie(categorie);
        return ResponseEntity.ok(categorieNoua);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> stergeCategorie(@PathVariable Integer id) {
        categorieCarteService.stergeCategorie(id);
        return ResponseEntity.noContent().build();
    }
}