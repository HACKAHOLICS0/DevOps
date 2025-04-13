package tn.esprit.tpfoyer.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    @Test
    void testCorsConfigurer() {
        CorsConfig corsConfig = new CorsConfig();
        WebMvcConfigurer configurer = corsConfig.corsConfigurer();
        
        assertNotNull(configurer);
        
        // Test the CORS configuration
        CorsRegistry registry = new CorsRegistry();
        configurer.addCorsMappings(registry);
        
        // Since CorsRegistry doesn't provide getters, we can only verify that the code executes without errors
        assertDoesNotThrow(() -> configurer.addCorsMappings(registry));
    }

    @Test
    void testCorsConfiguration() {
        CorsConfig corsConfig = new CorsConfig();
        WebMvcConfigurer configurer = corsConfig.corsConfigurer();
        
        // Create a test registry
        CorsRegistry registry = new CorsRegistry();
        
        // Test that multiple calls don't throw exceptions
        assertAll(
            () -> assertDoesNotThrow(() -> configurer.addCorsMappings(registry)),
            () -> assertDoesNotThrow(() -> configurer.addCorsMappings(new CorsRegistry()))
        );
    }
} 