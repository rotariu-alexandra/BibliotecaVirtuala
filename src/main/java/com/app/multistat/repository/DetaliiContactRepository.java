package com.app.multistat.repository;

import com.app.multistat.entity.DetaliiContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetaliiContactRepository extends JpaRepository<DetaliiContact, Integer> {

    DetaliiContact findByEmail(String email);
}