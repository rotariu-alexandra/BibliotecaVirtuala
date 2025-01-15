package com.app.multistat.repository;

import com.app.multistat.entity.IstoricActivitateUtilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IstoricActivitateUtilizatorRepository extends JpaRepository<IstoricActivitateUtilizator, Integer> {

    List<IstoricActivitateUtilizator> findByUtilizator_Id(Integer utilizatorId);
}