package tn.esprit.tpfoyer.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j // Simple Logging Façade for Java
public class BlocServiceImpl implements IBlocService {

    private final BlocRepository blocRepository;

    @Scheduled(fixedRate = 30000) // Exécution toutes les 30 secondes
    public List<Bloc> retrieveAllBlocs() {
        List<Bloc> listB = blocRepository.findAll();
        log.info("Nombre total de blocs : {}", listB.size());
        listB.forEach(b -> log.info("Bloc : {}", b));
        return listB;
    }

    @Transactional
    public List<Bloc> retrieveBlocsSelonCapacite(long capacite) {
        return blocRepository.findAll().stream()
                .filter(bloc -> bloc.getCapaciteBloc() >= capacite)
                .toList();
    }

    @Transactional
    public Bloc retrieveBloc(Long blocId) {
        return blocRepository.findById(blocId)
                .orElseThrow(() -> new RuntimeException("Bloc non trouvé avec l'ID : " + blocId));
    }

    public Bloc addBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    public Bloc modifyBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    public void removeBloc(Long blocId) {
        blocRepository.deleteById(blocId);
    }

    public List<Bloc> trouverBlocsSansFoyer() {
        return blocRepository.findAllByFoyerIsNull();
    }

    public List<Bloc> trouverBlocsParNomEtCap(String nom, long capacite) {
        return blocRepository.findAllByNomBlocAndCapaciteBloc(nom, capacite);
    }
}
