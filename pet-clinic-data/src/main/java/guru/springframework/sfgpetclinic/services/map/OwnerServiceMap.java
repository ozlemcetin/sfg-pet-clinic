package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

import java.util.List;
import java.util.stream.Collectors;


public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {


    @Override
    public Owner save(Owner object) {
        return super.save(object.getId(), object);
    }


    @Override
    public Owner findByLastName(String lastName) {

        List<Owner> list = super.findAll().stream().filter(object -> object.getLastName().equals(lastName)).collect(Collectors.toList());
        return list != null && list.size() > 0 ? list.get(0) : null;
    }
}
