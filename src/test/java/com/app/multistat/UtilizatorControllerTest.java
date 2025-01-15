package com.app.multistat;

import com.app.multistat.controller.UtilizatorController;
import com.app.multistat.entity.Utilizator;
import com.app.multistat.service.UtilizatorService;
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

@WebMvcTest(UtilizatorController.class)
public class UtilizatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtilizatorService utilizatorService;

    @Test
    void testGetUtilizatori() throws Exception {
        // Mock response
        Utilizator utilizator = new Utilizator();
        utilizator.setId(1);
        utilizator.setNume("Popescu");
        utilizator.setPrenume("Ion");
        utilizator.setEmail("ion.popescu@example.com");

        when(utilizatorService.obtineUtilizatori()).thenReturn(Collections.singletonList(utilizator));

        // Perform GET request
        mockMvc.perform(get("/api/utilizatori")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nume").value("Popescu"))
                .andExpect(jsonPath("$[0].email").value("ion.popescu@example.com"));
    }

    @Test
    void testGetUtilizatorById() throws Exception {
        // Mock response
        Utilizator utilizator = new Utilizator();
        utilizator.setId(1);
        utilizator.setNume("Popescu");
        utilizator.setPrenume("Ion");
        utilizator.setEmail("ion.popescu@example.com");

        when(utilizatorService.obtineUtilizator(1)).thenReturn(utilizator);

        // Perform GET request
        mockMvc.perform(get("/api/utilizatori/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nume").value("Popescu"))
                .andExpect(jsonPath("$.email").value("ion.popescu@example.com"));
    }

    @Test
    void testEditeazaUtilizator() throws Exception {
        // Mock response
        Utilizator utilizatorActualizat = new Utilizator();
        utilizatorActualizat.setId(1);
        utilizatorActualizat.setNume("Ionescu");
        utilizatorActualizat.setEmail("ionescu.maria@example.com");

        when(utilizatorService.editeazaUtilizator(Mockito.eq(1), Mockito.any(Utilizator.class))).thenReturn(utilizatorActualizat);

        // Perform PUT request
        mockMvc.perform(put("/api/utilizatori/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nume\": \"Ionescu\", \"email\": \"ionescu.maria@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nume").value("Ionescu"))
                .andExpect(jsonPath("$.email").value("ionescu.maria@example.com"));
    }

    @Test
    void testStergeUtilizator() throws Exception {
        // Perform DELETE request
        mockMvc.perform(delete("/api/utilizatori/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
