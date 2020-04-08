package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyServiceMap extends CrudMapService<Speciality, Long> implements SpecialtyService {


    @Override
    public Speciality findByDescription(String description) {
        return super.findAll().stream().filter(object -> object.getDescription().equals(description)).findAny().orElse(null);
    }
}
