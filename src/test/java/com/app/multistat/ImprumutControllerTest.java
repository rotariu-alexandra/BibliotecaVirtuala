package com.app.multistat;

import com.app.multistat.controller.ImprumutController;
import com.app.multistat.entity.Carte;
import com.app.multistat.entity.Imprumut;
import com.app.multistat.entity.Utilizator;
import com.app.multistat.service.ImprumutService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ImprumutController.class)
public class ImprumutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImprumutService imprumutService;

    @Test
    void testGetImprumuturi() throws Exception {
        // Mock response
        Utilizator utilizator = new Utilizator();
        utilizator.setId(1);
        utilizator.setNume("Popescu");

        Carte carte = new Carte();
        carte.setId(1);
        carte.setTitlu("Java Programming");

        Imprumut imprumut = new Imprumut(utilizator, carte, new Date(), new Date());
        imprumut.setId(1);

        when(imprumutService.obtineImprumuturi()).thenReturn(Collections.singletonList(imprumut));

        // Perform GET request
        mockMvc.perform(get("/api/imprumuturi")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].carte.id").value(1))
                .andExpect(jsonPath("$[0].utilizator.id").value(1));
    }

    @Test
    void testAdaugaImprumut() throws Exception {
        // Mock response
        Utilizator utilizator = new Utilizator();
        utilizator.setId(1);

        Carte carte = new Carte();
        carte.setId(1);

        Imprumut imprumut = new Imprumut(utilizator, carte, new Date(), new Date());
        imprumut.setId(2);

        when(imprumutService.adaugaImprumut(Mockito.any(Imprumut.class))).thenReturn(imprumut);

        // Perform POST request
        mockMvc.perform(post("/api/imprumuturi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"utilizator\": {\"id\": 1}, \"carte\": {\"id\": 1}, \"dataImprumut\": \"2025-01-13\", \"dataReturnare\": \"2025-01-20\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void testStergeImprumut() throws Exception {
        // Perform DELETE request
        mockMvc.perform(delete("/api/imprumuturi/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
