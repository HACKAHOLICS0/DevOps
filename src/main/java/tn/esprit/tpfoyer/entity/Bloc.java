package tn.esprit.tpfoyer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bloc {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long idBloc;

  String nomBloc;
  long capaciteBloc;

  @ManyToOne(cascade = CascadeType.ALL)
  Foyer foyer;

  @OneToMany(mappedBy = "bloc")
  @JsonIgnore
  @ToString.Exclude
  Set<Chambre> chambres = new HashSet<Chambre>();
}
