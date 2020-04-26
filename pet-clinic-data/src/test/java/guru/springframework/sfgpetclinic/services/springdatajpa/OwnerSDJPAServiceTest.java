package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJPAServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerSDJPAService ownerSDJPAService;

    final Long ownerId = 1L;
    final String lastName = "Weston";
    Owner owner1 = null;

    @BeforeEach
    void setUp() {

        //Lombok
        owner1 = Owner.builder()
                .id(ownerId)
                .firstName("Michael")
                .lastName(lastName)
                .address("191-103 Integer Rd. Corona New Mexico 08219")
                .city("New Mexico")
                .telephone("(404) 960-3807")
                .pets(new HashSet<>())
                .build();

    }

    @Test
    void findByLastName() {
        {
            when(ownerRepository.findByLastName(any())).thenReturn(owner1);
        }

        Owner owner = ownerSDJPAService.findByLastName(lastName);
        assertNotNull(owner);
        assertEquals(owner.getLastName(), lastName);
    }

    @Test
    void findAll() {
        {
            Set<Owner> owners = new HashSet<>();
            owners.add(owner1);
            when(ownerRepository.findAll()).thenReturn(owners);
        }

        Set<Owner> owners = ownerSDJPAService.findAll();
        assertNotNull(owners);
        assertEquals(owners.size(), 1);
    }

    @Test
    void findById() {
        {
            when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner1));
        }

        Owner owner = ownerSDJPAService.findById(ownerId);
        assertNotNull(owner);
        assertEquals(owner.getId(), ownerId);

    }

    void findById_NotFound() {
        {
            when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        }

        Owner owner = ownerSDJPAService.findById(ownerId);
        assertNull(owner);
    }

    @Test
    void save() {
        {
            when(ownerRepository.save(any())).thenReturn(owner1);
        }

        Owner owner = ownerSDJPAService.save(owner1);
        assertNotNull(owner);

    }

    @Test
    void delete() {
        ownerSDJPAService.delete(owner1);
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJPAService.deleteById(ownerId);

        /*
        default is 1 times, assumes 1 interaction
         */
        verify(ownerRepository).deleteById(anyLong());
    }
}