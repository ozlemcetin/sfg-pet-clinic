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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @InjectMocks
    OwnerController ownerController;

    @Mock
    OwnerService ownerService;

    MockMvc mockMvc;
    Owner owner1;

    @BeforeEach
    void setUp() {

        //mockMvc
        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerController)
                .build();

    }


    @Test
    void findOwner() throws Exception {

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"));

        //verify
        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindOwnerForm_NoOwnerFound() throws Exception {

        //given
        {
            List<Owner> owners = new ArrayList<>();
            when(ownerService.findAllByLastNameLike(anyString())).thenReturn(owners);
        }


        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"))
                //attributeDoesNotExist
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("ownerListSelections"));

        // TODO: 5/18/2020
        // bindingResult.rejectValue("lastName", errorMessage, errorMessage);

        //verify
        verify(ownerService).findAllByLastNameLike(anyString());
    }


    @Test
    void processFindOwnerForm_OneOwnerFound() throws Exception {

        //given
        Long ownerId = 1L;
        {
            List<Owner> owners = new ArrayList<>();

            //Lombok
            Owner owner1 = Owner.builder()
                    .id(ownerId)
                    .firstName("Michael")
                    .lastName("Weston")
                    .address("191-103 Integer Rd. Corona New Mexico 08219")
                    .city("New Mexico")
                    .telephone("(404) 960-3807")
                    .pets(new HashSet<>())
                    .build();
            owners.add(owner1);

            when(ownerService.findAllByLastNameLike(anyString())).thenReturn(owners);
        }


        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + ownerId))
                //attributeDoesNotExist
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("ownerListSelections"));


        //verify

        //verify
        verify(ownerService).findAllByLastNameLike(anyString());
    }

    @Test
    void processFindOwnerForm_MultipleOwnerFound() throws Exception {

        //given
        {
            List<Owner> owners = new ArrayList<>();

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

            Owner owner2 = Owner.builder()
                    .id(2L)
                    .lastName("Weston")
                    .build();
            owners.add(owner2);

            when(ownerService.findAllByLastNameLike(anyString())).thenReturn(owners);
        }


        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownersList"))
                //attributeExists
                .andExpect(MockMvcResultMatchers.model().attributeExists("ownerListSelections"))
                .andExpect(MockMvcResultMatchers.model().attribute("ownerListSelections",
                        Matchers.hasSize(2)));

        //verify

        //verify
        verify(ownerService).findAllByLastNameLike(anyString());
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