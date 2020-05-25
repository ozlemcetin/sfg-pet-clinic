package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

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

        return "owners/findOwners";
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
            String errorMessage = "Owner Not Found by the Last Name : " +  owner.getLastName();
            bindingResult.rejectValue("lastName", errorMessage, errorMessage);
            return "owners/findOwners";

        } else if (ownersByLastName.size() == 1) {

            //One owner found
            owner = ownersByLastName.get(0);
            return "redirect:/owners/" + owner.getId();

        } else {

            //Multiple owners found
            model.addAttribute("ownerListSelections", ownersByLastName);
            return "owners/ownersList";
        }

    }


    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {

        ModelAndView mav = new ModelAndView("owners/ownerDetails");

        /*
        puts owner obj in the model as attribute with the key "owner"
         */
        mav.addObject(ownerService.findById(ownerId));

        return mav;
    }

}
