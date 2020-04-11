package guru.springframework.sfgpetclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "specialities")
public class Speciality extends BaseEntity {

    private String description;

    //@ManyToMany(mappedBy = "specialties")
    //private Set<Vet> vets = new HashSet<>();

    /*
     Getters and Setters
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
