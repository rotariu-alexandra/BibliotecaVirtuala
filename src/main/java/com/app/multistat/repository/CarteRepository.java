package com.app.multistat.repository;

import com.app.multistat.entity.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarteRepository extends JpaRepository<Carte, Integer> {
    List<Carte> findByTitluContaining(String titlu);
    List<Carte> findByDomeniu(String domeniu); // Adaugă această metodă
}