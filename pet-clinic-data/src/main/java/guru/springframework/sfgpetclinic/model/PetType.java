package guru.springframework.sfgpetclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pet_types")
public class PetType extends BaseEntity {

    @Column(name = "name")
    private String name;

    /*
     This is because there is no need for a type to navigate to all the pets of this type.
     */
    //@OneToMany(mappedBy = "petType")
    //private Set<Pet> pets;


    /*
     Getters and Setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
