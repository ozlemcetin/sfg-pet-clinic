package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class PetMapService extends MyCrudMapService<Pet, Long> implements PetService {

    private final PetTypeService petTypeService;
    private final VisitService visitService;

    public PetMapService(PetTypeService petTypeService, VisitService visitService) {
        this.petTypeService = petTypeService;
        this.visitService = visitService;
    }

    @Override
    public Pet save(Pet object) {

        if (object.getPetType() == null) {
            throw new RuntimeException("To save a pet object, pet type is required and cannot be null.");
        }

        //Save Pet Type
        if (object != null && object.getPetType() != null) {

            //Persist Pet Type
            if (object.getPetType().getId() == null) {
                PetType savedPetType = petTypeService.save(object.getPetType());
                object.getPetType().setId(savedPetType.getId());
            }
        }

        //Save Visits
        if (object != null && object.getVisits() != null) {
            object.getVisits().forEach(visit -> {

                //Persist Visit
                if (visit.getId() == null) {
                    Visit savedVisit = visitService.save(visit);
                    visit.setId(savedVisit.getId());
                }

            });
        }

        return super.save(object);
    }
}
