package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getPet_petFound() {

        //given
        Owner owner = Owner.builder().id(2L).pets(new HashSet<>()).build();
        String petName = "Rosco";
        {
            Pet pet1 = new Pet();
            pet1.setName(petName);
            pet1.setOwner(owner);
            pet1.setBirthDate(LocalDate.now());
            owner.getPets().add(pet1);

            Pet pet2 = new Pet();
            pet2.setName("Curly");
            pet2.setOwner(owner);
            pet2.setBirthDate(LocalDate.now());
            owner.getPets().add(pet2);

            Pet pet3 = new Pet();
            pet3.setName(null);
            pet3.setOwner(owner);
            pet3.setBirthDate(LocalDate.now());
            owner.getPets().add(pet3);

        }

        //service
        Pet foundPet = owner.getPet(petName);

        //assertions
        assertNotNull(foundPet);
        assertEquals(foundPet.getName(), petName);
    }

    @Test
    void getPet_notFound() {

        //given
        Owner owner = Owner.builder().id(2L).pets(new HashSet<>()).build();
        String petName = "Rosco";

        //service
        Pet foundPet = owner.getPet(petName);

        //assertions
        assertNull(foundPet);

    }
}