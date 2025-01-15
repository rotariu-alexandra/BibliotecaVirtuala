
package com.app.multistat.service;

import com.app.multistat.entity.IstoricActivitateUtilizator;
import com.app.multistat.repository.IstoricActivitateUtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IstoricActivitateUtilizatorService {

    @Autowired
    private IstoricActivitateUtilizatorRepository istoricRepository;

    public List<IstoricActivitateUtilizator> obtineIstoric() {
        return istoricRepository.findAll();
    }
}
