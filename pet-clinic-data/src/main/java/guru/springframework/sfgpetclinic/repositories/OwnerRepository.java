package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
These interfaces are all JPA entities (as CrudRepository),
Spring Data JPA is going to provide us instances of these at runtime automatically
 */
public interface OwnerRepository extends CrudRepository<Owner, Long> {

    /*
    This is going to be using those dynamic query methods with a Spring Data Jpa.
     */
    Owner findByLastName(String lastName);

    List<Owner> findAllByLastNameLike(String lastName);
}
