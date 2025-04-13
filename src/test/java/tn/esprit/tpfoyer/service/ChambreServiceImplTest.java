package tn.esprit.tpfoyer.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;

import java.util.List;

 class ChambreServiceImplTest {

    @Mock
    private ChambreRepository chambreRepository;

    private ChambreServiceImpl chambreService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        chambreService = new ChambreServiceImpl(chambreRepository);
    }

    @Test
     void testRetrieveAllChambres() {
        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();
        when(chambreRepository.findAll()).thenReturn(List.of(chambre1, chambre2));

        List<Chambre> result = chambreService.retrieveAllChambres();
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
     void testAddChambre() {
        Chambre chambre = new Chambre();
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Chambre result = chambreService.addChambre(chambre);
        assertNotNull(result);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
     void testRetrieveChambre() {
        Long chambreId = 1L;
        Chambre chambre = new Chambre();
        when(chambreRepository.findById(chambreId)).thenReturn(java.util.Optional.of(chambre));

        Chambre result = chambreService.retrieveChambre(chambreId);
        assertNotNull(result);
        verify(chambreRepository, times(1)).findById(chambreId);
    }
}

