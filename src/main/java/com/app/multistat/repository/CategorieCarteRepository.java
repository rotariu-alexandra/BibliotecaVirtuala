package com.app.multistat.repository;

import com.app.multistat.entity.CategorieCarte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieCarteRepository extends JpaRepository<CategorieCarte, Integer> {

}