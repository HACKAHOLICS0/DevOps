package tn.esprit.tpfoyer.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

@Service
@AllArgsConstructor
public class EtudiantServiceImpl implements IEtudiantService {

  private final EtudiantRepository etudiantRepository;

  public List<Etudiant> retrieveAllEtudiants() {
    return etudiantRepository.findAll();
  }

  public Etudiant retrieveEtudiant(Long etudiantId) {
    Optional<Etudiant> etudiantOpt = etudiantRepository.findById(etudiantId);
    return etudiantOpt.orElseThrow(
        () -> new NoSuchElementException("Etudiant not found with ID: " + etudiantId));
  }

  public Etudiant addEtudiant(Etudiant etudiant) {
    return etudiantRepository.save(etudiant);
  }

  public Etudiant modifyEtudiant(Etudiant etudiant) {
    return etudiantRepository.save(etudiant);
  }

  public void removeEtudiant(Long etudiantId) {
    etudiantRepository.deleteById(etudiantId);
  }

  public Etudiant recupererEtudiantParCin(long cin) {
    return etudiantRepository.findEtudiantByCinEtudiant(cin);
  }
}
