package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    //public as being used in test scenarios
    public static final String VIEWS_PET_CONTROLLER_CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";


    private final PetTypeService petTypeService;
    private final OwnerService ownerService;
    private final PetService petService;

    public PetController(PetTypeService petTypeService, OwnerService ownerService, PetService petService) {
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
        this.petService = petService;
    }

    /*
        ModelAttribute means, whenever I come in to this controller,
        I want to be able to add in these attributes to the model.
    */
    @ModelAttribute("petTypes")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initPetCreationForm(Owner owner, Model model) {

        Pet pet = new Pet();
        pet.setOwner(owner);
        owner.getPets().add(pet);

        model.addAttribute("pet", pet);
        return VIEWS_PET_CONTROLLER_CREATE_OR_UPDATE_PET_FORM;
    }


    @PostMapping("/pets/new")
    public String processPetCreationForm(Owner owner, @Valid Pet pet,
                                         BindingResult bindingResult, Model model) {

        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            bindingResult.rejectValue("name", "duplicate", "Pet already exists!");
        }

        pet.setOwner(owner);
        owner.getPets().add(pet);

        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", pet);
            return VIEWS_PET_CONTROLLER_CREATE_OR_UPDATE_PET_FORM;

        } else {

            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }


    @GetMapping("/pets/{petId}/edit")
    public String initPetUpdateForm(@PathVariable Long petId, Model model) {

        model.addAttribute("pet", petService.findById(petId));
        return VIEWS_PET_CONTROLLER_CREATE_OR_UPDATE_PET_FORM;
    }

    /*
   We're runnning validation on the pet
    */
    @PostMapping("/pets/{petId}/edit")
    public String processPetUpdateForm(Owner owner, @Valid Pet pet,
                                       BindingResult bindingResult, @PathVariable Long petId, Model model) {

        pet.setOwner(owner);
        owner.getPets().add(pet);

        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", pet);
            return VIEWS_PET_CONTROLLER_CREATE_OR_UPDATE_PET_FORM;

        } else {
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }
}
