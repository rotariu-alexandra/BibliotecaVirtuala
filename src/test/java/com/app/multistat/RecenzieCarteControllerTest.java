package com.app.multistat;

import com.app.multistat.controller.RecenzieCarteController;
import com.app.multistat.entity.RecenzieCarte;
import com.app.multistat.service.RecenzieCarteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecenzieCarteController.class)
public class RecenzieCarteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecenzieCarteService recenzieCarteService;

    @Test
    void testGetRecenzii() throws Exception {
        // Mock response
        RecenzieCarte recenzie = new RecenzieCarte();
        recenzie.setId(1);
        recenzie.setRating(5);
        recenzie.setComentariu("Cartea este excelentă!");
        when(recenzieCarteService.obtineRecenzii()).thenReturn(Collections.singletonList(recenzie));

        // Perform GET request
        mockMvc.perform(get("/api/recenzii")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].rating").value(5))
                .andExpect(jsonPath("$[0].comentariu").value("Cartea este excelentă!"));
    }

    @Test
    void testAdaugaRecenzie() throws Exception {
        // Mock response
        RecenzieCarte recenzieNoua = new RecenzieCarte();
        recenzieNoua.setId(2);
        recenzieNoua.setRating(4);
        recenzieNoua.setComentariu("Cartea este bună.");
        when(recenzieCarteService.adaugaRecenzie(Mockito.any(RecenzieCarte.class))).thenReturn(recenzieNoua);

        // Perform POST request
        mockMvc.perform(post("/api/recenzii")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rating\": 4, \"comentariu\": \"Cartea este bună.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.comentariu").value("Cartea este bună."));
    }

    @Test
    void testStergeRecenzie() throws Exception {
        // Perform DELETE request
        mockMvc.perform(delete("/api/recenzii/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
