package com.app.multistat.controller;

import com.app.multistat.entity.Utilizator;
import com.app.multistat.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilizatori")
public class UtilizatorController {

    @Autowired
    private UtilizatorService utilizatorService;

    @GetMapping
    public ResponseEntity<List<Utilizator>> getUtilizatori() {
        List<Utilizator> utilizatori = utilizatorService.obtineUtilizatori();
        return ResponseEntity.ok(utilizatori);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilizator> getUtilizatorById(@PathVariable Integer id) {
        Utilizator utilizator = utilizatorService.obtineUtilizator(id);
        return ResponseEntity.ok(utilizator);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilizator> editeazaUtilizator(@PathVariable Integer id, @RequestBody Utilizator utilizatorActualizat) {
        Utilizator utilizatorEditat = utilizatorService.editeazaUtilizator(id, utilizatorActualizat);
        return ResponseEntity.ok(utilizatorEditat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> stergeUtilizator(@PathVariable Integer id) {
        utilizatorService.stergeUtilizator(id);
        return ResponseEntity.noContent().build();
    }
}