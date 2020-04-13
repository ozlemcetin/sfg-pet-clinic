package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "specialities")
public class Speciality extends BaseEntity {

    private String description;

    //@ManyToMany(mappedBy = "specialties")
    //private Set<Vet> vets = new HashSet<>();

    /*
     Getters and Setters
     */

}
