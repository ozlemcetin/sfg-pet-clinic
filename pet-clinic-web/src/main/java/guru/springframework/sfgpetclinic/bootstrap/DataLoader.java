package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType savedDogPetType = null;
        {
            PetType dog = new PetType();
            dog.setName("Dog");

            savedDogPetType = petTypeService.save(dog);
        }

        PetType savedCatPetType = null;
        {
            PetType cat = new PetType();
            cat.setName("Cat");

            savedCatPetType = petTypeService.save(cat);
        }

        PetType savedBirdPetType = null;
        {
            PetType bird = new PetType();
            bird.setName("Bird");

            savedBirdPetType = petTypeService.save(bird);
        }

        System.out.println("Pet Types Loaded.");
        System.out.println("Number of Pet Types: " + petTypeService.findAll().size());


        {
            Owner owner1 = new Owner();
            owner1.setFirstName("Michael");
            owner1.setLastName("Weston");

            ownerService.save(owner1);
        }


        {
            Owner owner2 = new Owner();
            owner2.setFirstName("Fiona");
            owner2.setLastName("Glenanne");

            ownerService.save(owner2);
        }


        {
            Owner owner3 = new Owner();
            owner3.setFirstName("Ozlem");
            owner3.setLastName("Cetin");

            ownerService.save(owner3);
        }

        System.out.println("Owners Loaded.");
        System.out.println("Number of Owners: " + ownerService.findAll().size());

        {

            Vet vet1 = new Vet();
            vet1.setFirstName("Sam");
            vet1.setLastName("Axe");

            vetService.save(vet1);
        }

        {
            Vet vet2 = new Vet();
            vet2.setFirstName("Jessie");
            vet2.setLastName("Porter");

            vetService.save(vet2);
        }

        System.out.println("Vets Loaded.");
        System.out.println("Number of Vets: " + vetService.findAll().size());


    }
}
