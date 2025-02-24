package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entity.Bloc;

import java.util.List;

@Repository
public interface BlocRepository extends JpaRepository<Bloc, Long> {

 // Récupérer les Blocs qui ont une capacité supérieure à 50
 List<Bloc> findAllByCapaciteBlocGreaterThan(long c);

 // Récupérer tous les Blocs qui ont un nom qui commence par "Bl"
 List<Bloc> findAllByNomBlocStartingWith(String c);

 // Récupérer tous les blocs qui ont un nom donné et une capacité donnée
 List<Bloc> findAllByNomBlocAndCapaciteBloc(String nom, long capacite);

 // Récupérer le bloc qui a un nom donné
 Bloc findByNomBloc(String nom);

 // Récupérer un bloc par nom et une capacité donnée
 Bloc findBlocByNomBlocAndCapaciteBlocGreaterThan(String nb, long c);

 // Liste des blocs non affectés à aucun foyer
 List<Bloc> findAllByFoyerIsNull();
}
