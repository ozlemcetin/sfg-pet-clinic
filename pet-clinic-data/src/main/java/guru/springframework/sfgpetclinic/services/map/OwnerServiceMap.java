package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerServiceMap extends CrudMapService<Owner, Long> implements OwnerService {


    @Override
    public Owner findByLastName(String lastName) {

        List<Owner> list = super.findAll().stream().filter(object -> object.getLastName().equals(lastName)).collect(Collectors.toList());
        return list != null && list.size() > 0 ? list.get(0) : null;
    }
}
