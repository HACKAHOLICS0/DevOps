package tn.esprit.tpfoyer.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.service.IBlocService;
import tn.esprit.tpfoyer.service.IChambreService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChambreRestControllerTest {

    @Mock
    private IChambreService chambreService;

    @InjectMocks
    private ChambreRestController chambreRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetChambres() {
        // Arrange
        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();
        List<Chambre> expectedList = Arrays.asList(chambre1, chambre2);

        when(chambreService.retrieveAllChambres()).thenReturn(expectedList);

        // Act
        List<Chambre> actualList = chambreRestController.getChambres();

        // Assert
        assertEquals(expectedList, actualList);
        verify(chambreService, times(1)).retrieveAllChambres();
    }

    @Test
    void testRetrieveChambre() {
        // Arrange
        Long id = 1L;
        Chambre expected = new Chambre();
        expected.setIdChambre(id);

        when(chambreService.retrieveChambre(id)).thenReturn(expected);

        // Act
        Chambre actual = chambreRestController.retrieveChambre(id);

        // Assert
        assertEquals(expected, actual);
        verify(chambreService, times(1)).retrieveChambre(id);
    }

    @Test
    void testAddChambre() {
        // Arrange
        Chambre toAdd = new Chambre();
        Chambre added = new Chambre();
        added.setIdChambre(1L);

        when(chambreService.addChambre(toAdd)).thenReturn(added);

        // Act
        Chambre result = chambreRestController.addChambre(toAdd);

        // Assert
        assertEquals(added, result);
        verify(chambreService, times(1)).addChambre(toAdd);
    }

    @Test
    void testRemoveChambre() {
        // Arrange
        Long id = 1L;

        // Act
        chambreRestController.removeChambre(id);

        // Assert
        verify(chambreService, times(1)).removeChambre(id);
    }

    @Test
    void testModifyChambre() {
        // Arrange
        Chambre toModify = new Chambre();
        toModify.setIdChambre(1L);
        Chambre modified = new Chambre();
        modified.setIdChambre(1L);

        when(chambreService.modifyChambre(toModify)).thenReturn(modified);

        // Act
        Chambre result = chambreRestController.modifyChambre(toModify);

        // Assert
        assertEquals(modified, result);
        verify(chambreService, times(1)).modifyChambre(toModify);
    }

    @Test
    void testTrouverChSelonTC() {
        // Arrange
        TypeChambre type = TypeChambre.SIMPLE;
        Chambre chambre = new Chambre();
        chambre.setTypeC(type);
        List<Chambre> expectedList = Arrays.asList(chambre);

        when(chambreService.recupererChambresSelonTyp(type)).thenReturn(expectedList);

        // Act
        List<Chambre> result = chambreRestController.trouverChSelonTC(type);

        // Assert
        assertEquals(expectedList, result);
        verify(chambreService, times(1)).recupererChambresSelonTyp(type);
    }

    @Test
    void testTrouverChSelonEt() {
        // Arrange
        long cin = 12345678L;
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);

        when(chambreService.trouverchambreSelonEtudiant(cin)).thenReturn(chambre);

        // Act
        Chambre result = chambreRestController.trouverChSelonEt(cin);

        // Assert
        assertEquals(chambre, result);
        verify(chambreService, times(1)).trouverchambreSelonEtudiant(cin);
    }

    @Nested
    class BlocRestControllerTest {

        @Mock
        private IBlocService blocService;

        @InjectMocks
        private BlocRestController blocRestController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testGetBlocs() {
            // Arrange
            Bloc bloc1 = new Bloc();
            Bloc bloc2 = new Bloc();
            List<Bloc> expectedBlocs = Arrays.asList(bloc1, bloc2);

            when(blocService.retrieveAllBlocs()).thenReturn(expectedBlocs);

            // Act
            List<Bloc> actualBlocs = blocRestController.getBlocs();

            // Assert
            assertEquals(expectedBlocs, actualBlocs);
            verify(blocService, times(1)).retrieveAllBlocs();
        }

        @Test
        void testRetrieveBloc() {
            // Arrange
            Long blocId = 1L;
            Bloc expectedBloc = new Bloc();
            expectedBloc.setIdBloc(blocId);

            when(blocService.retrieveBloc(blocId)).thenReturn(expectedBloc);

            // Act
            Bloc actualBloc = blocRestController.retrieveBloc(blocId);

            // Assert
            assertEquals(expectedBloc, actualBloc);
            verify(blocService, times(1)).retrieveBloc(blocId);
        }

        @Test
        void testAddBloc() {
            // Arrange
            Bloc blocToAdd = new Bloc();
            Bloc expectedBloc = new Bloc();
            expectedBloc.setIdBloc(1L);

            when(blocService.addBloc(blocToAdd)).thenReturn(expectedBloc);

            // Act
            Bloc actualBloc = blocRestController.addBloc(blocToAdd);

            // Assert
            assertEquals(expectedBloc, actualBloc);
            verify(blocService, times(1)).addBloc(blocToAdd);
        }

        @Test
        void testRemoveBloc() {
            // Arrange
            Long blocId = 1L;

            // Act
            blocRestController.removeBloc(blocId);

            // Assert
            verify(blocService, times(1)).removeBloc(blocId);
        }

        @Test
        void testModifyBloc() {
            // Arrange
            Bloc blocToModify = new Bloc();
            blocToModify.setIdBloc(1L);
            Bloc expectedBloc = new Bloc();
            expectedBloc.setIdBloc(1L);

            when(blocService.modifyBloc(blocToModify)).thenReturn(expectedBloc);

            // Act
            Bloc actualBloc = blocRestController.modifyBloc(blocToModify);

            // Assert
            assertEquals(expectedBloc, actualBloc);
            verify(blocService, times(1)).modifyBloc(blocToModify);
        }

        @Test
        void testGetBlocsWithoutFoyer() {
            // Arrange
            Bloc bloc1 = new Bloc();
            Bloc bloc2 = new Bloc();
            List<Bloc> expectedBlocs = Arrays.asList(bloc1, bloc2);

            when(blocService.trouverBlocsSansFoyer()).thenReturn(expectedBlocs);

            // Act
            List<Bloc> actualBlocs = blocRestController.getBlocswirhoutFoyer();

            // Assert
            assertEquals(expectedBlocs, actualBlocs);
            verify(blocService, times(1)).trouverBlocsSansFoyer();
        }

        @Test
        void testRecuperBlocsParNomEtCap() {
            // Arrange
            String nom = "Bloc A";
            long capacite = 100;
            Bloc bloc1 = new Bloc();
            List<Bloc> expectedBlocs = Arrays.asList(bloc1);

            when(blocService.trouverBlocsParNomEtCap(nom, capacite)).thenReturn(expectedBlocs);

            // Act
            List<Bloc> actualBlocs = blocRestController.recuperBlocsParNomEtCap(nom, capacite);

            // Assert
            assertEquals(expectedBlocs, actualBlocs);
            verify(blocService, times(1)).trouverBlocsParNomEtCap(nom, capacite);
        }
    }
}
