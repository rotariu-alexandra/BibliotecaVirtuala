package com.app.multistat;

import com.app.multistat.controller.CategorieCarteController;
import com.app.multistat.entity.CategorieCarte;
import com.app.multistat.service.CategorieCarteService;
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

@WebMvcTest(CategorieCarteController.class)
public class CategorieCarteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategorieCarteService categorieCarteService;

    @Test
    void testGetCategorii() throws Exception {
        // Mock response
        CategorieCarte categorie = new CategorieCarte(1, "Ficțiune");
        when(categorieCarteService.obtineCategorii()).thenReturn(Collections.singletonList(categorie));

        // Perform GET request
        mockMvc.perform(get("/api/categorii")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Ficțiune"));
    }

    @Test
    void testAdaugaCategorie() throws Exception {
        // Mock response
        CategorieCarte categorieNoua = new CategorieCarte(2, "Știință");
        when(categorieCarteService.adaugaCategorie(Mockito.any(CategorieCarte.class))).thenReturn(categorieNoua);

        // Perform POST request
        mockMvc.perform(post("/api/categorii")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Știință\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Știință"));
    }

    @Test
    void testStergeCategorie() throws Exception {
        // Perform DELETE request
        mockMvc.perform(delete("/api/categorii/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
