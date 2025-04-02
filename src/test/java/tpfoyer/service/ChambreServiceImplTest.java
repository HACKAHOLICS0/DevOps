package tpfoyer.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ChambreServiceImplTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllChambres() {
        // Arrange
        List<Chambre> chambres = new ArrayList<>();
        Chambre chambre1 = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        Chambre chambre2 = new Chambre(2L, 102, TypeChambre.DOUBLE, null, null);
        chambres.add(chambre1);
        chambres.add(chambre2);
        when(chambreRepository.findAll()).thenReturn(chambres);

        // Act
        List<Chambre> result = chambreService.retrieveAllChambres();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveChambre() {
        // Arrange
        Long chambreId = 1L;
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.findById(chambreId)).thenReturn(Optional.of(chambre));

        // Act
        Chambre result = chambreService.retrieveChambre(chambreId);

        // Assert
        assertNotNull(result);
        assertEquals(101, result.getNumeroChambre());
    }

    @Test
    void testAddChambre() {
        // Arrange
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        // Act
        Chambre result = chambreService.addChambre(chambre);

        // Assert
        assertNotNull(result);
        assertEquals(101, result.getNumeroChambre());
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testModifyChambre() {
        // Arrange
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        // Act
        Chambre result = chambreService.modifyChambre(chambre);

        // Assert
        assertNotNull(result);
        assertEquals(101, result.getNumeroChambre());
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testRemoveChambre() {
        // Arrange
        Long chambreId = 1L;
        doNothing().when(chambreRepository).deleteById(chambreId);

        // Act
        chambreService.removeChambre(chambreId);

        // Assert
        verify(chambreRepository, times(1)).deleteById(chambreId);
    }

    @Test
    void testRecupererChambresSelonTyp() {
        // Arrange
        TypeChambre typeChambre = TypeChambre.SIMPLE;
        List<Chambre> chambres = new ArrayList<>();
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        chambres.add(chambre);
        when(chambreRepository.findAllByTypeC(typeChambre)).thenReturn(chambres);

        // Act
        List<Chambre> result = chambreService.recupererChambresSelonTyp(typeChambre);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(chambreRepository, times(1)).findAllByTypeC(typeChambre);
    }

    @Test
    void testTrouverChambreSelonEtudiant() {
        // Arrange
        long cin = 123456;
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.trouverChselonEt(cin)).thenReturn(chambre);

        // Act
        Chambre result = chambreService.trouverchambreSelonEtudiant(cin);

        // Assert
        assertNotNull(result);
        assertEquals(101, result.getNumeroChambre());
        verify(chambreRepository, times(1)).trouverChselonEt(cin);
    }
}