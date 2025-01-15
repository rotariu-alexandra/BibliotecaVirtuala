

        package com.app.multistat.controller;

import com.app.multistat.entity.Imprumut;
import com.app.multistat.service.ImprumutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imprumuturi")
public class ImprumutController {

    @Autowired
    private ImprumutService imprumutService;

    @GetMapping
    public ResponseEntity<List<Imprumut>> getImprumuturi() {
        List<Imprumut> imprumuturi = imprumutService.obtineImprumuturi();
        return ResponseEntity.ok(imprumuturi);
    }

    @PostMapping
    public ResponseEntity<Imprumut> adaugaImprumut(@RequestBody Imprumut imprumut) {
        Imprumut imprumutNou = imprumutService.adaugaImprumut(imprumut);
        return ResponseEntity.ok(imprumutNou);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> stergeImprumut(@PathVariable Integer id) {
        imprumutService.stergeImprumut(id);
        return ResponseEntity.noContent().build();
    }
}
