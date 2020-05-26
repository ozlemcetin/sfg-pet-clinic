package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @InjectMocks
    VisitController visitController;

    @Mock
    PetService petService;

    @Mock
    VisitService visitService;


    private MockMvc mockMvc;
    private Pet pet;
    private Map<String, String> uriVariables;


    @BeforeEach
    void setUp() {

        //mockMvc
        mockMvc = MockMvcBuilders
                .standaloneSetup(visitController)
                .build();

        Long petId = 1L;
        Long ownerId = 1L;
        {

            Owner owner = Owner.builder()
                    .id(ownerId)
                    .lastName("Doe")
                    .firstName("Joe")
                    .build();

            PetType petType = PetType.builder()
                    .name("Dog").build();

            pet = Pet.builder()
                    .id(petId)
                    .birthDate(LocalDate.of(2018, 11, 13))
                    .name("Cutie")
                    .visits(new HashSet<>())
                    .owner(owner)
                    .petType(petType)
                    .build();
        }


        //uriVariables
        uriVariables = new HashMap<>();
        uriVariables.put("ownerId", ownerId.toString());
        uriVariables.put("petId", petId.toString());
    }

    @Test
    void initNewVisitForm() throws Exception {

        //given
        {
            when(petService.findById(anyLong())).thenReturn(pet);
        }

        UriTemplate visitsNewUriTemplate = new UriTemplate("/owners/{ownerId}/pets/{petId}/visits/new");
        URI visitsUri = visitsNewUriTemplate.expand(uriVariables);

        mockMvc.perform(MockMvcRequestBuilders.get(visitsUri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(VisitController.VIEWS_VISIT_CONTROLLER_CREATE_OR_UPDATE_VISIT_FORM))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("visit"));

        //verify
        verify(petService).findById(anyLong());
    }


    @Test
    void processNewVisitForm() throws Exception {

        //given
        {
            when(petService.findById(anyLong())).thenReturn(pet);
        }

        UriTemplate visitsNewUriTemplate = new UriTemplate("/owners/{ownerId}/pets/{petId}/visits/new");
        URI visitsUri = visitsNewUriTemplate.expand(uriVariables);

        mockMvc.perform(MockMvcRequestBuilders.post(visitsUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date", "2018-11-11")
                .param("description", "Yet another visit"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(VisitController.REDIRECT_OWNERS_OWNERID))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("visit"));

        //verify
        verify(petService).findById(anyLong());
    }

}
