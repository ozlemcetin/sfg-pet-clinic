package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

@Service
public class OwnerMapService extends MyCrudMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Owner findByLastName(String lastName) {
        return super.findAll().stream().filter(object -> object.getLastName().equals(lastName)).findAny().orElse(null);
    }

    @Override
    public Owner save(Owner object) {

        //Save Pets
        if (object != null && object.getPets() != null) {
            object.getPets().forEach(pet -> {

                if (pet.getPetType() == null) {
                    throw new RuntimeException("To save an owner object, pet type is required and cannot be null.");
                }

                //Persist Pet Type
                if (pet.getPetType().getId() == null) {
                    PetType savedPetType = petTypeService.save(pet.getPetType());
                    pet.getPetType().setId(savedPetType.getId());
                }

                //Persist Pet
                if (pet.getId() == null) {
                    Pet savedPet = petService.save(pet);
                    pet.setId(savedPet.getId());
                }


            });
        }

        //Save Owner
        return super.save(object);
    }


}
