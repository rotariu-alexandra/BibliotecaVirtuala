package com.app.multistat.repository;

import com.app.multistat.entity.RecenzieCarte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecenzieCarteRepository extends JpaRepository<RecenzieCarte, Integer> {

    List<RecenzieCarte> findByCarte_Id(Integer carteId);

    List<RecenzieCarte> findByUtilizator_Id(Integer utilizatorId);
}