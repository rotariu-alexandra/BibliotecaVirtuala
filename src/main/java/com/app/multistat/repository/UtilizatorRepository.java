package com.app.multistat.repository;

import com.app.multistat.entity.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilizatorRepository extends JpaRepository<Utilizator, Integer> {

    List<Utilizator> findByRol(String rol);

    Utilizator findByDetaliiContact_Email(String email);
}
