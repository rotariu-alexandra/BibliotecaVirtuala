
package com.app.multistat.service;

import com.app.multistat.entity.Imprumut;
import com.app.multistat.repository.ImprumutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImprumutService {

    @Autowired
    private ImprumutRepository imprumutRepository;

    public List<Imprumut> obtineImprumuturi() {
        return imprumutRepository.findAll();
    }

    public Imprumut adaugaImprumut(Imprumut imprumut) {
        return imprumutRepository.save(imprumut);
    }

    public void stergeImprumut(Integer id) {
        imprumutRepository.deleteById(id);
    }
}