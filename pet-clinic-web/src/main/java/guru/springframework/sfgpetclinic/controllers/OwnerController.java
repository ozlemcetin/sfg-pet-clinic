package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    //public as being used in test scenarios
    public static final String VIEWS_OWNER_CONTROLLER_FIND_OWNERS = "owners/findOwners";
    public static final String VIEWS_OWNER_CONTROLLER_OWNERS_LIST = "owners/ownersList";
    public static final String VIEWS_OWNER_CONTROLLER_OWNER_DETAILS = "owners/ownerDetails";
    public static final String VIEWS_OWNER_CONTROLLER_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";


    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /*
    We don't allow the web forms to address and manipulate the ID property
     */
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {

        //BaseEntity
        dataBinder.setDisallowedFields("id");
    }


    @RequestMapping("/find")
    public String findOwner(Model model) {

        Owner owner = Owner.builder().build();
        model.addAttribute("owner", owner);
        return VIEWS_OWNER_CONTROLLER_FIND_OWNERS;
    }

    /*
     An Errors/BindingResult argument is expected to be declared immediately after the model attribute,
     the @RequestBody or the @RequestPart arguments to which they apply:
     */
    @GetMapping({"", "/"})
    public String processFindOwnerForm(Owner owner,
                                       BindingResult bindingResult, Model model) {

        /*
          allow parameterless GET request for /owners to return all records
         */
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        String lastNameStr = "%" + owner.getLastName() + "%";
        List<Owner> ownersByLastName = ownerService.findAllByLastNameLike(lastNameStr);

        if (ownersByLastName.isEmpty()) {

            //No owners found
            String errorMessage = "Owner Not Found by the Last Name : " + owner.getLastName();
            bindingResult.rejectValue("lastName", errorMessage, errorMessage);
            return VIEWS_OWNER_CONTROLLER_FIND_OWNERS;

        } else if (ownersByLastName.size() == 1) {

            //One owner found
            owner = ownersByLastName.get(0);
            return "redirect:/owners/" + owner.getId();

        } else {

            //Multiple owners found
            model.addAttribute("ownerListSelections", ownersByLastName);
            return VIEWS_OWNER_CONTROLLER_OWNERS_LIST;
        }

    }


    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {

        /*
        puts owner obj in the model as attribute with the key "owner"
         */
        ModelAndView mav = new ModelAndView(VIEWS_OWNER_CONTROLLER_OWNER_DETAILS);
        mav.addObject(ownerService.findById(ownerId));
        return mav;
    }

    @GetMapping("/new")
    public String initOwnerCreationForm(Model model) {

        Owner owner = Owner.builder().build();
        model.addAttribute("owner", owner);
        return VIEWS_OWNER_CONTROLLER_CREATE_OR_UPDATE_OWNER_FORM;
    }


    @PostMapping("/new")
    public String processOwnerCreationForm(@Valid Owner owner, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return VIEWS_OWNER_CONTROLLER_CREATE_OR_UPDATE_OWNER_FORM;

        } else {
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initOwnerUpdateForm(@PathVariable Long ownerId, Model model) {

        model.addAttribute("owner", ownerService.findById(ownerId));
        return VIEWS_OWNER_CONTROLLER_CREATE_OR_UPDATE_OWNER_FORM;
    }

    /*
    We're runnning validation on the owner
     */
    @PostMapping("/{ownerId}/edit")
    public String processOwnerUpdateForm(@Valid Owner owner, BindingResult bindingResult, @PathVariable Long ownerId) {

        if (bindingResult.hasErrors()) {
            return VIEWS_OWNER_CONTROLLER_CREATE_OR_UPDATE_OWNER_FORM;

        } else {
            owner.setId(ownerId);
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }
}
