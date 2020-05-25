package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    final Long ownerId = 1L;
    final String lastName = "Weston";

    @BeforeEach
    void setUp() {

        PetTypeService petTypeService = new PetTypeMapService();
        VisitService visitService = new VisitMapService();
        PetService petService = new PetMapService(petTypeService, visitService);
        ownerMapService = new OwnerMapService(petService);

        {
            //Lombok
            Owner owner1 = Owner.builder()
                    .id(ownerId)
                    .firstName("Michael")
                    .lastName(lastName)
                    .address("191-103 Integer Rd. Corona New Mexico 08219")
                    .city("New Mexico")
                    .telephone("(404) 960-3807")
                    .pets(new HashSet<>())
                    .build();

            ownerMapService.save(owner1);
        }
    }

    @Test
    void findAll() {
        assertEquals(ownerMapService.findAll().size(), 1);
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);
        assertNotNull(owner);
        assertEquals(owner.getId(), ownerId);
    }

    @Test
    void save() {

        Owner savedOwner = null;
        {
            Owner owner2 = new Owner();
            owner2.setFirstName("Fiona");
            owner2.setLastName("Glenanne");
            owner2.setAddress("Ap #867-859 Sit Rd. Azusa New York 39531");
            owner2.setCity("New York");
            owner2.setTelephone("(793) 151-6230");

            savedOwner = ownerMapService.save(owner2);
        }

        //If not defined, assigns id.
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());


    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));
        assertEquals(ownerMapService.findAll().size(), 0);
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);
        assertEquals(ownerMapService.findAll().size(), 0);
    }

    @Test
    void findByLastName() {
        Owner owner = ownerMapService.findByLastName(lastName);
        assertNotNull(owner);
        assertEquals(owner.getLastName(), lastName);
    }

    @Test
    void findAllByLastNameLike_NoOwnerFound() {

        String surname = "Cetin";
        List<Owner> owners = ownerMapService.findAllByLastNameLike(surname);

        assertNotNull(owners);
        assertEquals(owners.size(), 0);
    }

    @Test
    void findAllByLastNameLike_OneOwnerFound() {


        List<Owner> owners = ownerMapService.findAllByLastNameLike(lastName);
        assertNotNull(owners);
        assertEquals(owners.size(), 1);
        assertEquals(owners.get(0).getLastName(), lastName);
    }

    @Test
    void findAllByLastNameLike_ManyOwnersFound() {

        {
            Owner owner2 = new Owner();
            owner2.setFirstName("Fiona");
            owner2.setLastName(lastName);
            owner2.setAddress("Ap #867-859 Sit Rd. Azusa New York 39531");
            owner2.setCity("New York");
            owner2.setTelephone("(793) 151-6230");

            ownerMapService.save(owner2);
        }

        List<Owner> owners = ownerMapService.findAllByLastNameLike(lastName);
        assertNotNull(owners);
        assertEquals(owners.size(), 2);
    }


}