package com.app.multistat.repository;

import com.app.multistat.entity.Imprumut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImprumutRepository extends JpaRepository<Imprumut, Integer> {

    List<Imprumut> findByUtilizator_Id(Integer utilizatorId);

    List<Imprumut> findByCarte_Id(Integer carteId);
}