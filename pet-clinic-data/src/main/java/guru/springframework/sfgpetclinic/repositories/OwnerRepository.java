package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

/*
These interfaces are all JPA entities (as CrudRepository),
Spring Data JPA is going to provide us instances of these at runtime automatically
 */
public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
