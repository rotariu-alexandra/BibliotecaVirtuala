package com.app.multistat.repository;

import com.app.multistat.entity.AdresaUtilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdresaUtilizatorRepository extends JpaRepository<AdresaUtilizator, Integer> {

}