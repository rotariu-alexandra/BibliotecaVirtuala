package com.app.multistat;
import com.app.multistat.controller.CarteController;


import com.app.multistat.entity.Carte;
import com.app.multistat.service.CarteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarteController.class)
class CarteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarteService carteService;

    @Test
    void testGetCarti() throws Exception {
        // Mock the service response
        Carte carte = new Carte();
        carte.setId(1);
        carte.setTitlu("Carte Test");
        when(carteService.obtineCarti()).thenReturn(Collections.singletonList(carte));

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/carti")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titlu").value("Carte Test"));
    }

    @Test
    void testAdaugaCarte() throws Exception {
        // Mock the service response
        Carte carteNoua = new Carte();
        carteNoua.setId(2);
        carteNoua.setTitlu("Carte Noua");
        when(carteService.adaugaCarte(Mockito.any(Carte.class))).thenReturn(carteNoua);

        // Perform the POST request
        mockMvc.perform(post("/api/carti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titlu\": \"Carte Noua\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.titlu").value("Carte Noua"));
    }

    @Test
    void testStergeCarte() throws Exception {
        // Perform the DELETE request
        mockMvc.perform(delete("/api/carti/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
