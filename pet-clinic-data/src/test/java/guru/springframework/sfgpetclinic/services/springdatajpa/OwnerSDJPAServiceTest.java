package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJPAServiceTest {

    @InjectMocks
    OwnerSDJPAService ownerSDJPAService;

    @Mock
    OwnerRepository ownerRepository;


    final Long ownerId = 1L;
    final String lastName = "Weston";
    Owner owner1;

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

        //given
        {
            when(ownerRepository.findByLastName(any())).thenReturn(owner1);
        }

        //when
        Owner owner = ownerSDJPAService.findByLastName(lastName);
        assertNotNull(owner);
        assertEquals(owner.getLastName(), lastName);

        //verify
        verify(ownerRepository).findByLastName((any()));
    }

    @Test
    void findAllByLastNameLike_NoOwnerFound() {

        //given
        {
            List<Owner> ownerList = new ArrayList<>();
            when(ownerRepository.findAllByLastNameLike(any())).thenReturn(ownerList);
        }

        //when
        List<Owner> owners = ownerSDJPAService.findAllByLastNameLike(lastName);
        assertNotNull(owners);
        assertEquals(owners.size(), 0);

        //verify
        verify(ownerRepository).findAllByLastNameLike((any()));
    }

    @Test
    void findAllByLastNameLike_OneOwnerFound() {

        //given
        {
            List<Owner> ownerList = new ArrayList<>();
            ownerList.add(owner1);

            when(ownerRepository.findAllByLastNameLike(any())).thenReturn(ownerList);
        }

        //when
        List<Owner> owners = ownerSDJPAService.findAllByLastNameLike(lastName);
        assertNotNull(owners);
        assertEquals(owners.size(), 1);
        assertEquals(owners.get(0).getId(), ownerId);
        assertEquals(owners.get(0).getLastName(), lastName);

        //verify
        verify(ownerRepository).findAllByLastNameLike((any()));
    }

    @Test
    void findAllByLastNameLike_ManyOwnersFound() {

        //given
        {
            List<Owner> ownerList = new ArrayList<>();
            ownerList.add(owner1);

            Owner owner2 = new Owner();
            owner2.setFirstName("Fiona");
            owner2.setLastName(lastName);
            owner2.setAddress("Ap #867-859 Sit Rd. Azusa New York 39531");
            owner2.setCity("New York");
            owner2.setTelephone("(793) 151-6230");
            ownerList.add(owner2);

            when(ownerRepository.findAllByLastNameLike(any())).thenReturn(ownerList);
        }

        //when
        List<Owner> owners = ownerSDJPAService.findAllByLastNameLike(lastName);
        assertNotNull(owners);
        assertEquals(owners.size(), 2);
        assertEquals(owners.get(0).getLastName(), lastName);
        assertEquals(owners.get(1).getLastName(), lastName);


        //verify
        verify(ownerRepository).findAllByLastNameLike((any()));
    }

    @Test
    void findAll() {

        //given
        {
            Set<Owner> owners = new HashSet<>();
            owners.add(owner1);

            when(ownerRepository.findAll()).thenReturn(owners);
        }

        //when
        Set<Owner> owners = ownerSDJPAService.findAll();
        assertNotNull(owners);
        assertEquals(owners.size(), 1);

        //verify
        verify(ownerRepository).findAll();
    }

    @Test
    void findById() {

        //given
        {
            when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner1));
        }

        //when
        Owner owner = ownerSDJPAService.findById(ownerId);
        assertNotNull(owner);
        assertEquals(owner.getId(), ownerId);

        //verify
        verify(ownerRepository).findById(anyLong());

    }

    void findById_NotFound() {

        //given
        {
            when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        }

        Owner owner = ownerSDJPAService.findById(ownerId);
        assertNull(owner);

        //verify
        verify(ownerRepository).findById(anyLong());
    }

    @Test
    void save() {

        //given
        {
            when(ownerRepository.save(any())).thenReturn(owner1);
        }

        Owner owner = ownerSDJPAService.save(owner1);
        assertNotNull(owner);
        assertEquals(owner.getId(), ownerId);

        //verify
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {

        ownerSDJPAService.delete(owner1);

        //verify
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        ownerSDJPAService.deleteById(ownerId);

        /*
        default is 1 times, assumes 1 interaction
         */
        //verify
        verify(ownerRepository).deleteById(anyLong());
    }
}