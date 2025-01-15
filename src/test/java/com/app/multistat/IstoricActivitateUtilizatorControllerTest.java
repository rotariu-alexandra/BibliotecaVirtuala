package com.app.multistat;

import com.app.multistat.controller.IstoricActivitateUtilizatorController;
import com.app.multistat.entity.IstoricActivitateUtilizator;
import com.app.multistat.service.IstoricActivitateUtilizatorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IstoricActivitateUtilizatorController.class)
public class IstoricActivitateUtilizatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IstoricActivitateUtilizatorService istoricService;

    @Test
    void testGetIstoric() throws Exception {
        // Mock response
        IstoricActivitateUtilizator istoric = new IstoricActivitateUtilizator();
        istoric.setId(1);
        istoric.setActivitate("Împrumutat carte");
        istoric.setDataActivitate(new Date());

        when(istoricService.obtineIstoric()).thenReturn(Collections.singletonList(istoric));

        // Perform GET request
        mockMvc.perform(get("/api/istoric")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].activitate").value("Împrumutat carte"));
    }
}
