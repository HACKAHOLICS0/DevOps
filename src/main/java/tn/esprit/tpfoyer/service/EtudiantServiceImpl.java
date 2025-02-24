package tn.esprit.tpfoyer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EtudiantServiceImpl implements IEtudiantService {

    private final EtudiantRepository etudiantRepository;

    @Override
    public List<Etudiant> retrieveAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @Override
    public Etudiant retrieveEtudiant(Long etudiantId) {
        return etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec l'ID : " + etudiantId));
    }

    @Override
    public Etudiant addEtudiant(Etudiant c) {
        return etudiantRepository.save(c);
    }

    @Override
    public Etudiant modifyEtudiant(Etudiant c) {
        return etudiantRepository.save(c);
    }

    @Override
    public void removeEtudiant(Long etudiantId) {
        if (etudiantRepository.existsById(etudiantId)) {
            etudiantRepository.deleteById(etudiantId);
        } else {
            throw new RuntimeException("Impossible de supprimer : Étudiant introuvable avec l'ID : " + etudiantId);
        }
    }

    @Override
    public Etudiant recupererEtudiantParCin(long cin) {
        return Optional.ofNullable(etudiantRepository.findEtudiantByCinEtudiant(cin))
                .orElseThrow(() -> new RuntimeException("Aucun étudiant trouvé avec le CIN : " + cin));
    }
}
