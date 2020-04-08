package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
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
    private final SpecialtyService specialtyService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetService petService, PetTypeService petTypeService, SpecialtyService specialtyService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
    }

    @Override
    public void run(String... args) throws Exception {

        if (ownerService.findAll().size() == 0) {
            loadData();
        }
    }

    private void loadData() {
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
            Speciality radiology = new Speciality();
            radiology.setDescription("Radiology");
            Speciality savedRadiology = specialtyService.save(radiology);

        }

        System.out.println("Specialities Loaded.");
        System.out.println("Number of Specialities: " + specialtyService.findAll().size());

        {

            Vet vet1 = new Vet();
            vet1.setFirstName("Sam");
            vet1.setLastName("Axe");

            vet1.getSpecialties().add(specialtyService.findByDescription("Radiology"));

            vetService.save(vet1);
        }

        {
            Vet vet2 = new Vet();
            vet2.setFirstName("Jessie");
            vet2.setLastName("Porter");

            {

                Speciality surgery = new Speciality();
                surgery.setDescription("Surgery");
                vet2.getSpecialties().add(surgery);

            }

            {
                Speciality dentistry = new Speciality();
                dentistry.setDescription("Dentistry");
                vet2.getSpecialties().add(dentistry);
            }


            vetService.save(vet2);
        }

        System.out.println("Vets Loaded.");
        System.out.println("Number of Vets: " + vetService.findAll().size());

        System.out.println("Specialities Loaded.");
        System.out.println("Number of Specialities: " + specialtyService.findAll().size());

    }
}
