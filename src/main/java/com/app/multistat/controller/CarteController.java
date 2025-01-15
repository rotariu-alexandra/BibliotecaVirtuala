package com.app.multistat.controller;

import com.app.multistat.entity.Carte;
import com.app.multistat.service.CarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carti")
public class CarteController {

    @Autowired
    private CarteService carteService;

    @GetMapping
    public ResponseEntity<List<Carte>> getCarti() {
        List<Carte> carti = carteService.obtineCarti();
        return ResponseEntity.ok(carti);
    }

    @PostMapping
    public ResponseEntity<Carte> adaugaCarte(@RequestBody Carte carte) {
        Carte carteNoua = carteService.adaugaCarte(carte);
        return ResponseEntity.ok(carteNoua);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> stergeCarte(@PathVariable Integer id) {
        carteService.stergeCarte(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cautare")
    public ResponseEntity<List<Carte>> cautaCarti(@RequestParam String criteriu) {
        List<Carte> rezultate = carteService.cautaCarti(criteriu);
        return ResponseEntity.ok(rezultate);
    }

    @GetMapping("/filtrare")
    public ResponseEntity<List<Carte>> filtreazaCarti(@RequestParam String domeniu) {
        List<Carte> rezultate = carteService.filtreazaCarti(domeniu);
        return ResponseEntity.ok(rezultate);
        
    }
}
