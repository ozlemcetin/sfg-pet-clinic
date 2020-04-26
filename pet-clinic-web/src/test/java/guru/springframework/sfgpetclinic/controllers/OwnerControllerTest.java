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

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    MockMvc mockMvc;
    Set<Owner> owners;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerController)
                .build();

        owners = new HashSet<>();
        {
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
        }
    }

    @Test
    void listOwners() throws Exception {
        {
            when(ownerService.findAll()).thenReturn(owners);
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/index"))
                .andExpect(MockMvcResultMatchers.model().attribute("owners", Matchers.hasSize(1)));

        verify(ownerService).findAll();
    }


    @Test
    void findOwner() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("notImplemented"));

        verifyNoInteractions(ownerService);
    }
}