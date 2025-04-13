package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.tpfoyer.TpFoyerApplication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
