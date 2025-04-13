package tn.esprit.tpfoyer.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tn.esprit.controller.MetricsController;

import static org.mockito.Mockito.*; // 👈 import nécessaire
import static org.mockito.ArgumentMatchers.*; // 👈 import nécessaire
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = tn.esprit.controller.MetricsController.class)
@Import(tn.esprit.controller.MetricsController.class)
public class MetricsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeterRegistry meterRegistry;

    @BeforeEach
    void setup() {
        Counter mockCounter = mock(Counter.class);
        when(meterRegistry.counter(eq("custom_metric"))).thenReturn(mockCounter);
    }

    @Test
    void testCustomMetricEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/metrics/custom"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Custom metric incremented!"));
    }
}
