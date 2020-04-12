package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class SpecialtyMapService extends MyCrudMapService<Speciality, Long> implements SpecialtyService {


    @Override
    public Speciality findByDescription(String description) {
        return super.findAll().stream().filter(object -> object.getDescription().equals(description)).findAny().orElse(null);
    }
}
