package com.htlimst.lieferrex.controller.LoginRegistration;

import com.htlimst.lieferrex.dto.AdressDto;
import com.htlimst.lieferrex.dto.KundeRegistrationDto;
import com.htlimst.lieferrex.dto.MandantRegistrationDto;
import com.htlimst.lieferrex.dto.PasswortAendernDto;
import com.htlimst.lieferrex.service.kunde.KundeService;
import com.htlimst.lieferrex.service.mandant.MandantService;
import com.htlimst.lieferrex.service.security.UserPojo;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import com.htlimst.lieferrex.service.security.UserPrincipalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginRegistrationController {

    public KundeService kundeService;
    public UserPrincipalDetailsService userPrincipalDetailsService;
    public MandantService mandantService;

    @Autowired
    public LoginRegistrationController(KundeService kundeService, UserPrincipalDetailsService userPrincipalDetailsService, MandantService mandantService) {
        this.kundeService = kundeService;
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        this.mandantService = mandantService;
    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "main/login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "main/register";
    }

    @ModelAttribute("user")
    public KundeRegistrationDto userRegistrationDto() {
        return new KundeRegistrationDto();
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") @Valid KundeRegistrationDto registrationDto, BindingResult result, Model model) {
        List<String> returnVal = new ArrayList<>();

        UserPojo existingEmail = userPrincipalDetailsService.findUserByEmail(registrationDto.getEmail());
        if (existingEmail != null) {
            returnVal.add("Benutzer mit dieser E-Mail Adresse existiert bereits");
            model.addAttribute("returnVal", returnVal);
            return "main/register";
        }

        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                returnVal.add(error.getDefaultMessage());
                model.addAttribute("returnVal", returnVal);
            }
            List<ObjectError> globalError = result.getGlobalErrors();
            for (ObjectError error : globalError) {
                returnVal.add(error.getDefaultMessage());
                model.addAttribute("returnVal", returnVal);
            }
            return "main/register";
        }



        if (!kundeService.save(registrationDto)) {
            returnVal.add("Addresse konnte nicht gefunden werden");
            model.addAttribute("returnVal", returnVal);
            return "main/register";

        }

        returnVal.add("Benutzer erfolgreich erstellt");
        model.addAttribute("returnVal", returnVal);
        return "main/register";

    }

    @GetMapping("/successLogin")
    public String redirectSuccesLogin(RedirectAttributes redirectAttrs, @AuthenticationPrincipal UserPrincipal principal) {
        System.out.println(principal.getAuthorities());
        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_KUNDE"))) {
            redirectAttrs.addAttribute("login", "success");
            return "redirect:/";
        } else if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANGESTELLTER")) || principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANDANT"))) {
            System.out.println("test");
            return "redirect:/dashboard";
        } else {
            return "redirect:/";
        }
    }


    @ModelAttribute("mandant")
    public MandantRegistrationDto mandantRegistrationDto() {
        return new MandantRegistrationDto();
    }

    @GetMapping("/restaurantpartner")
    public String showRestaurantpartner() {
        return "main/restaurantpartner.html";
    }

    @PostMapping("/restaurantpartner")
    public String registerMandantenAccount(@ModelAttribute("mandant") @Valid MandantRegistrationDto registrationDto, BindingResult result, Model model) {
        List<String> returnVal = new ArrayList<>();


        UserPojo existingEmail = userPrincipalDetailsService.findUserByEmail(registrationDto.getEmail());
        if (existingEmail != null) {
            returnVal.add("Benutzer mit dieser E-Mail Adresse existiert bereits");
            model.addAttribute("returnVal", returnVal);
            return "main/restaurantpartner.html";
        }
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                returnVal.add(error.getDefaultMessage());
            }
            List<ObjectError> globalError = result.getGlobalErrors();
            for (ObjectError error : globalError) {
                returnVal.add(error.getDefaultMessage());
            }
            model.addAttribute("returnVal", returnVal);
            return "main/restaurantpartner.html";
        }

        if (!mandantService.saveRegistrationDto(registrationDto)) {
            returnVal.add("Addresse konnte nicht gefunden werden");
            model.addAttribute("returnVal", returnVal);
            return "main/restaurantpartner.html";
        }

        returnVal.add("Mandant erfolgreich erstellt");
        model.addAttribute("returnVal", returnVal);
        return "main/restaurantpartner.html";

    }


    @ModelAttribute("pw")
    public PasswortAendernDto passwortAendernDto() {
        return new PasswortAendernDto();
    }

    @GetMapping("/changePassword")
    public String showChangePasswordPage() {
        return "main/changepassword.html";
    }


    @PostMapping("/changePassword")
    public String changePasswort(@ModelAttribute("pw") @Valid PasswortAendernDto passwortAendernDto, BindingResult result, @AuthenticationPrincipal UserPrincipal principal, Model model) {
        List<String> returnVal = new ArrayList<>();
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error : fieldErrors) {
                returnVal.add(error.getDefaultMessage());
            }
            List<ObjectError> globalError = result.getGlobalErrors();
            for (ObjectError error : globalError) {
                returnVal.add(error.getDefaultMessage());
            }
            model.addAttribute("returnVal", returnVal);
            return "main/changepassword.html";
        }

        if (!kundeService.passwortAendern(passwortAendernDto, principal)) {
            returnVal.add("Passwort konnte nicht geändert werden");
            model.addAttribute("returnVal", returnVal);
            return "main/changepassword.html";
        }


        returnVal.add("Erfolgreich geändert");

        model.addAttribute("returnVal", returnVal);
        return "main/changepassword.html";
    }


    @GetMapping("/changeAddress")
    public String showChangeAddressPage() {
        return "main/changeaddress.html";
    }


    @ModelAttribute("adresse")
    public AdressDto adresseAendernDto() {
        return new AdressDto();
    }

    @PostMapping("/changeAddress")
    public String changeAddress(@ModelAttribute("adresse") AdressDto adressDto, @AuthenticationPrincipal UserPrincipal principal) {
        if (adressDto.getPlaceId().isEmpty()){
            return "redirect:/changeAddress?NA";
        }
        if (!kundeService.adresseAendern(adressDto, principal)) {
            return "redirect:/changeAddress?error";
        }

        return "redirect:/changeAddress?success";
    }

}
