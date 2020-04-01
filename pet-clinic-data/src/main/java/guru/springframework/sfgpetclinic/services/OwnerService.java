package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Pet;

public interface OwnerService extends CrudService<Pet, Long> {

    Pet findByLastName(String lastName);


}
