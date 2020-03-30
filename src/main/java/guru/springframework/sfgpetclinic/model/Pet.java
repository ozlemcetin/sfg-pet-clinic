package guru.springframework.sfgpetclinic.model;

import java.time.LocalDate;
import java.util.Locale;

public class Pet {

    private PetType  petType;
    private Owner owner;
    private LocalDate localeDate;

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LocalDate getLocaleDate() {
        return localeDate;
    }

    public void setLocaleDate(LocalDate localeDate) {
        this.localeDate = localeDate;
    }
}
