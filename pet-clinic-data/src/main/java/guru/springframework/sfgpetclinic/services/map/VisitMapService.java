package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Service;

@Service
public class VisitMapService extends MyCrudMapService<Visit, Long> implements VisitService {


}
