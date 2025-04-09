package tn.esprit.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class MetricsController {
    private final MeterRegistry meterRegistry;

    public MetricsController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/custom")
    public String customMetric() {
        meterRegistry.counter("custom_metric").increment();
        return "Custom metric incremented!";
    }
} 