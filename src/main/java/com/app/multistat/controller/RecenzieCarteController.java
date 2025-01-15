package com.app.multistat.controller;

import com.app.multistat.entity.RecenzieCarte;
import com.app.multistat.service.RecenzieCarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recenzii")
public class RecenzieCarteController {

    @Autowired
    private RecenzieCarteService recenzieCarteService;

    @GetMapping
    public ResponseEntity<List<RecenzieCarte>> getRecenzii() {
        List<RecenzieCarte> recenzii = recenzieCarteService.obtineRecenzii();
        return ResponseEntity.ok(recenzii);
    }

    @PostMapping
    public ResponseEntity<RecenzieCarte> adaugaRecenzie(@RequestBody RecenzieCarte recenzie) {
        RecenzieCarte recenzieNoua = recenzieCarteService.adaugaRecenzie(recenzie);
        return ResponseEntity.ok(recenzieNoua);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> stergeRecenzie(@PathVariable Integer id) {
        recenzieCarteService.stergeRecenzie(id);
        return ResponseEntity.noContent().build();
    }
}