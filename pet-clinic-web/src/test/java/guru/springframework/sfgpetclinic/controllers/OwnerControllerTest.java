package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @InjectMocks
    OwnerController ownerController;

    @Mock
    OwnerService ownerService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        //mockMvc
        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerController)
                .build();

    }

    @Test
    void listOwners() throws Exception {

        //given
        {
            Set<Owner> owners = new HashSet<>();

            //Lombok
            Owner owner1 = Owner.builder()
                    .id(1L)
                    .firstName("Michael")
                    .lastName("Weston")
                    .address("191-103 Integer Rd. Corona New Mexico 08219")
                    .city("New Mexico")
                    .telephone("(404) 960-3807")
                    .pets(new HashSet<>())
                    .build();
            owners.add(owner1);

            Owner owner2 = Owner.builder().id(2L).build();
            owners.add(owner2);

            when(ownerService.findAll()).thenReturn(owners);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owners"))
                .andExpect(MockMvcResultMatchers.model().attribute("owners",
                        Matchers.hasSize(2)));

        //verify
        verify(ownerService).findAll();
    }


    @Test
    void findOwner() throws Exception {

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("notImplemented"));

        //verify
        verifyNoInteractions(ownerService);
    }

    @Test
    void showOwner() throws Exception {

        //given
        Long ownerId = 2L;
        {
            Owner owner = Owner.builder().id(ownerId).build();
            when(ownerService.findById(anyLong())).thenReturn(owner);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/" + ownerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownerDetails"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attribute("owner",
                        Matchers.hasProperty("id",
                                Matchers.is(ownerId))));

        //verify
        verify(ownerService).findById(anyLong());
    }
}