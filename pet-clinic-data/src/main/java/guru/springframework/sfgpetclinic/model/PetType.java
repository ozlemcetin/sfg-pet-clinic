package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pet_types")
public class PetType extends BaseEntity {

    @Column(name = "name")
    private String name;

    /*
     This is because there is no need for a type to navigate to all the pets of this type.
     */
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "petType")
    //private Set<Pet> pets;


    /*
     Getters and Setters
     */

}
