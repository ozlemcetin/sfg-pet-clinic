package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 test.
 *
 * @author <a href="mailto:k.czechowski83@gmail.com">Krzysztof Czechowski</a>
 */

class PetMapServiceTest {

    private PetMapService petMapService;
    private final Long petId = 1L;

    @BeforeEach
    void setUp() {

        PetTypeService petTypeService = new PetTypeMapService();
        VisitService visitService = new VisitMapService();
        petMapService = new PetMapService(petTypeService, visitService);

        {
            PetType petType = PetType.builder().name("Dog").build();
            Pet pet = Pet.builder().id(petId).petType(petType).build();
            petMapService.save(pet);
        }

    }

    @Test
    void findAll() {

        Set<Pet> petSet = petMapService.findAll();
        assertEquals(1, petSet.size());
    }

    @Test
    void findByIdExistingId() {

        Pet pet = petMapService.findById(petId);
        assertNotNull(pet);
        assertEquals(petId, pet.getId());
    }

    @Test
    void findByIdNotExistingId() {

        Long otherPetId = 5L;
        Pet pet = petMapService.findById(otherPetId);
        assertNull(pet);
    }

    @Test
    void findByIdNullId() {

        Long otherPetId = null;
        Pet pet = petMapService.findById(otherPetId);
        assertNull(pet);
    }

    @Test
    void saveExistingId() {

        Long otherPetId = 5L;
        Pet savedPet = null;
        {
            PetType petType = PetType.builder().name("Dog").build();
            Pet pet2 = Pet.builder().id(otherPetId).petType(petType).build();
            savedPet = petMapService.save(pet2);
        }

        assertNotNull(savedPet);
        assertEquals(otherPetId, savedPet.getId());
        assertEquals(2, petMapService.findAll().size());
    }

    @Test
    void saveDuplicateId() {

        Pet savedPet = null;
        {
            PetType petType = PetType.builder().name("Cat").build();
            Pet pet2 = Pet.builder().id(petId).petType(petType).build();
            savedPet = petMapService.save(pet2);
        }

        assertNotNull(savedPet);
        assertEquals(petId, savedPet.getId());
        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void saveNoId() {

        Pet savedPet = null;
        {
            PetType petType = PetType.builder().name("Bird").build();
            Pet pet2 = Pet.builder().petType(petType).build();
            savedPet = petMapService.save(pet2);
        }

        assertNotNull(savedPet);
        assertNotNull(savedPet.getId());
        assertEquals(2, petMapService.findAll().size());
    }

    @Test
    void deletePet() {

        petMapService.delete(petMapService.findById(petId));
        assertEquals(0, petMapService.findAll().size());

    }

    @Test
    void deleteWithWrongId() {

        Long otherPetId = 5L;
        petMapService.delete(petMapService.findById(otherPetId));
        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void deleteWithNullId() {

        Pet pet = Pet.builder().build();
        petMapService.delete(pet);

        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void deleteNull() {

        petMapService.delete(null);
        assertEquals(1, petMapService.findAll().size());

    }

    @Test
    void deleteByIdCorrectId() {

        petMapService.deleteById(petId);

        assertEquals(0, petMapService.findAll().size());
    }

    @Test
    void deleteByIdWrongId() {

        petMapService.deleteById(5L);

        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void deleteByIdNullId() {

        petMapService.deleteById(null);

        assertEquals(1, petMapService.findAll().size());
    }
}