package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class OwnerMapService extends MyCrudMapService<Owner, Long> implements OwnerService {


    private final PetService petService;

    public OwnerMapService(PetService petService) {
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
