package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tn.esprit.tpfoyer.TpFoyerApplication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TpFoyerApplicationTest {

    @Test
    @Disabled("Désactivé car dépend du contexte Spring ou de la BDD")
    void mainRunsWithoutException() {
        assertDoesNotThrow(() -> {
            String[] args = {};
            TpFoyerApplication.main(args);
        });
    }

}
