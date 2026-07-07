package br.edu.iff.ccc.caronaamiga.controller.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MainViewController.class)
class MainViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnInicialViewAndModelAttributes() throws Exception {
        mockMvc.perform(get("/").param("id", "123").param("var1", "ola"))
            .andExpect(status().isOk())
            .andExpect(view().name("inicial"))
            .andExpect(model().attribute("id", 123L))
            .andExpect(model().attribute("var1", "ola"))
            .andExpect(model().attribute("app.title", "Carona Amiga"));
    }
}
