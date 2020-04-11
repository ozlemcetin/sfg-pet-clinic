package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Service;

@Service
public class PetServiceMap extends CrudServiceMap<Pet, Long> implements PetService {

    private final VisitService visitService;

    public PetServiceMap(VisitService visitService) {
        this.visitService = visitService;
    }

    @Override
    public Pet save(Pet object) {

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
