package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @InjectMocks
    PetController petController;

    @Mock
    PetTypeService petTypeService;

    @Mock
    OwnerService ownerService;

    @Mock
    PetService petService;

    MockMvc mockMvc;

    Set<PetType> petTypeSet;
    Owner owner;
    Long ownerId;


    @BeforeEach
    void setUp() {

        //mockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        //petTypeSet
        petTypeSet = new HashSet<>();
        petTypeSet.add(PetType.builder().id(5L).name("Dog").build());
        petTypeSet.add(PetType.builder().id(7L).name("Cat").build());

        //owner
        ownerId = 2L;
        owner = Owner.builder().id(ownerId).pets(new HashSet<>()).build();

    }

    @Test
    void initPetCreationForm() throws Exception {

        //given
        {
            when(petTypeService.findAll()).thenReturn(petTypeSet);
            when(ownerService.findById(anyLong())).thenReturn(owner);
        }


        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/" + ownerId + "/pets/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(PetController.VIEWS_PET_CONTROLLER_CREATE_OR_UPDATE_PET_FORM))
                .andExpect(MockMvcResultMatchers.model().attributeExists("petTypes"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pet"));


        //verify
        verify(petTypeService).findAll();
        verify(ownerService).findById(anyLong());
    }


    @Test
    void processPetCreationForm() throws Exception {

        //given
        {
            when(petTypeService.findAll()).thenReturn(petTypeSet);
            when(ownerService.findById(anyLong())).thenReturn(owner);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/owners/" + ownerId + "/pets/new"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + ownerId));


        //verify
        verify(petTypeService).findAll();
        verify(ownerService).findById(anyLong());
    }

    void initPetUpdateForm() throws Exception {

        //given
        Long petId = 5L;
        {
            when(petTypeService.findAll()).thenReturn(petTypeSet);
            when(ownerService.findById(anyLong())).thenReturn(owner);

            Pet pet = Pet.builder().id(petId).build();
            when(petService.findById(anyLong())).thenReturn(pet);
        }


        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/" + ownerId + "/pets/" + petId + "/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(PetController.VIEWS_PET_CONTROLLER_CREATE_OR_UPDATE_PET_FORM))
                .andExpect(MockMvcResultMatchers.model().attributeExists("petTypes"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pet"));


        //verify
        verify(petTypeService).findAll();
        verify(ownerService).findById(anyLong());
        verify(petService).findById(anyLong());
    }

    @Test
    void processPetUpdateForm() throws Exception {

        //given
        Long petId = 5L;
        {
            when(petTypeService.findAll()).thenReturn(petTypeSet);
            when(ownerService.findById(anyLong())).thenReturn(owner);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/owners/" + ownerId + "/pets/" + petId + "/edit"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + ownerId));


        //verify
        verify(petTypeService).findAll();
        verify(ownerService).findById(anyLong());
    }
}