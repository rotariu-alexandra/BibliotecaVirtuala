package com.app.multistat.repository;

import com.app.multistat.entity.DetaliiCarte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetaliiCarteRepository extends JpaRepository<DetaliiCarte, Integer> {

}