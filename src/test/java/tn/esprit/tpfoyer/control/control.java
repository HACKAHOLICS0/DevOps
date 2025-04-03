package tn.esprit.tpfoyer.control;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FoyerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllFoyers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/foyers"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}