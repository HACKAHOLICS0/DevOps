package tn.esprit.tpfoyer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entity.Bloc;

import java.util.List;
import java.util.Optional;


@Repository
 public interface BlocRepository extends JpaRepository<Bloc, Long> {





















   List<Bloc> findAllByCapaciteBlocGreaterThan(long c);



















    List<Bloc> findAllByNomBlocStartingWith(String c);


    List<Bloc> findAllByNomBlocAndCapaciteBloc (String nom , long capacite );


 Optional<Bloc> findByNomBloc(String nomBloc);















    /* No need to code CRUD here. Its is already in the
    interfaces provided by the framework Spring Data JPA :
       - CrudRepository or
       - PagingAndSortingRepository or
       - JpaRepository
     */


    Bloc findBlocByNomBlocAndCapaciteBlocGreaterThan(String nb, long c);

    // List des blocs non affectés à aucun foyer :
    List<Bloc> findAllByFoyerIsNull();





}
