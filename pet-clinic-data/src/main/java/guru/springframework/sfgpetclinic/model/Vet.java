package guru.springframework.sfgpetclinic.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vets")
public class Vet extends Person {

    /*
    By deafult, ManyToMany relationships LAZILY initialized.
    By EAGER, JPA tries to load everything all at once
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vets_specialities",
            joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Speciality> specialties = new HashSet<>();


    /*
     Getters and Setters
     */
    public Set<Speciality> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Speciality> specialties) {
        this.specialties = specialties;
    }
}
