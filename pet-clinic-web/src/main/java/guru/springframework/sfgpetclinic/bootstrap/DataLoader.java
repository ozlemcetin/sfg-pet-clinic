package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetService petService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {


        PetType dog = new PetType();
        dog.setName("Dog");

        PetType cat = new PetType();
        cat.setName("Cat");

        PetType bird = new PetType();
        bird.setName("Bird");


        {
            Owner owner1 = new Owner();
            owner1.setFirstName("Michael");
            owner1.setLastName("Weston");
            owner1.setAddress("191-103 Integer Rd. Corona New Mexico 08219");
            owner1.setCity("New Mexico");
            owner1.setTelephone("(404) 960-3807");


            {
                Pet pet1 = new Pet();
                pet1.setName("Rosco");
                pet1.setPetType(dog);
                pet1.setOwner(owner1);
                pet1.setBirthDate(LocalDate.now());
                owner1.getPets().add(pet1);
            }

            {
                Pet pet2 = new Pet();
                pet2.setName("Curly");
                pet2.setPetType(cat);
                pet2.setOwner(owner1);
                pet2.setBirthDate(LocalDate.now());
                owner1.getPets().add(pet2);
            }

            ownerService.save(owner1);
        }


        {
            Owner owner2 = new Owner();
            owner2.setFirstName("Fiona");
            owner2.setLastName("Glenanne");
            owner2.setAddress("Ap #867-859 Sit Rd. Azusa New York 39531");
            owner2.setCity("New York");
            owner2.setTelephone("(793) 151-6230");

            {
                Pet pet1 = new Pet();
                pet1.setName("Coco");
                pet1.setPetType(bird);
                pet1.setOwner(owner2);
                pet1.setBirthDate(LocalDate.now());
                owner2.getPets().add(pet1);
            }

            ownerService.save(owner2);
        }


        {
            Owner owner3 = new Owner();
            owner3.setFirstName("Ozlem");
            owner3.setLastName("Cetin");
            owner3.setAddress("5037 Diam Rd. Daly City Ohio 90255");
            owner3.setCity("Ohio    ");
            owner3.setTelephone("(453) 391-4650");

            {
                Pet pet1 = new Pet();
                pet1.setName("Puma");
                pet1.setPetType(dog);
                pet1.setOwner(owner3);
                pet1.setBirthDate(LocalDate.now());
                owner3.getPets().add(pet1);
            }

            {
                Pet pet2 = new Pet();
                pet2.setName("Nada");
                pet2.setPetType(dog);
                pet2.setOwner(owner3);
                pet2.setBirthDate(LocalDate.now());
                owner3.getPets().add(pet2);
            }

            ownerService.save(owner3);
        }

        System.out.println("Owners Loaded.");
        System.out.println("Number of Owners: " + ownerService.findAll().size());

        System.out.println("Pets Loaded.");
        System.out.println("Number of Pets: " + petService.findAll().size());


        System.out.println("Pet Types Loaded.");
        System.out.println("Number of Pet Types: " + petTypeService.findAll().size());


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
