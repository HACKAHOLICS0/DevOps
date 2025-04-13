package tn.esprit.tpfoyer.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.List;
import java.util.Optional;

class EtudiantServiceImplTest {

   @Mock
   private EtudiantRepository etudiantRepository;

   private EtudiantServiceImpl etudiantService;

   @BeforeEach
   void setUp() {
      MockitoAnnotations.openMocks(this);
      etudiantService = new EtudiantServiceImpl(etudiantRepository);
   }

   @Test
   void testRetrieveAllEtudiants() {
      Etudiant etudiant1 = new Etudiant();
      Etudiant etudiant2 = new Etudiant();
      when(etudiantRepository.findAll()).thenReturn(List.of(etudiant1, etudiant2));

      List<Etudiant> result = etudiantService.retrieveAllEtudiants();
      assertEquals(2, result.size());
      verify(etudiantRepository, times(1)).findAll();
   }

   @Test
   void testAddEtudiant() {
      Etudiant etudiant = new Etudiant();
      when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

      Etudiant result = etudiantService.addEtudiant(etudiant);
      assertNotNull(result);
      verify(etudiantRepository, times(1)).save(etudiant);
   }

   @Test
   void testRetrieveEtudiant() {
      Long etudiantId = 1L;
      Etudiant etudiant = new Etudiant();
      when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));

      Etudiant result = etudiantService.retrieveEtudiant(etudiantId);
      assertNotNull(result);
      verify(etudiantRepository, times(1)).findById(etudiantId);
   }

   @Test
   void testRetrieveEtudiantThrowsException() {
      Long etudiantId = 1L;
      when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.empty());

      RuntimeException exception = assertThrows(RuntimeException.class, () -> {
         etudiantService.retrieveEtudiant(etudiantId);
      });
      assertEquals("Etudiant not found with id: " + etudiantId, exception.getMessage());
      verify(etudiantRepository, times(1)).findById(etudiantId);
   }

   @Test
   void testModifyEtudiant() {
      Etudiant etudiant = new Etudiant();
      when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

      Etudiant result = etudiantService.modifyEtudiant(etudiant);
      assertNotNull(result);
      verify(etudiantRepository, times(1)).save(etudiant);
   }

   @Test
   void testRemoveEtudiant() {
      Long etudiantId = 1L;
      doNothing().when(etudiantRepository).deleteById(etudiantId);

      etudiantService.removeEtudiant(etudiantId);

      verify(etudiantRepository, times(1)).deleteById(etudiantId);
   }

   @Test
   void testRecupererEtudiantParCin() {
      long cin = 12345L;
      Etudiant etudiant = new Etudiant();
      when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(etudiant);

      Etudiant result = etudiantService.recupererEtudiantParCin(cin);
      assertNotNull(result);
      verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(cin);
   }

   @Test
   void testRecupererEtudiantParCinThrowsException() {
      long cin = 12345L;
      when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(null);

      Etudiant result = etudiantService.recupererEtudiantParCin(cin);
      assertNull(result);
      verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(cin);
   }
}
