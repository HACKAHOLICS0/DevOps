package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.Test;
import tn.esprit.tpfoyer.TpFoyerApplication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TpFoyerApplicationTest {

    @Test
    void mainRunsWithoutException() {
        assertDoesNotThrow(() -> {
            String[] args = {};
            TpFoyerApplication.main(args); // Si tu veux capturer une exception ici, gère-la autrement
        });
    }
}
