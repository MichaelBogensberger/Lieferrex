package com.htlimst.lieferrex.controller;

import com.htlimst.lieferrex.dto.KundeRegistrationDto;
import com.htlimst.lieferrex.dto.MandantRegistrationDto;
import com.htlimst.lieferrex.service.kunde.KundeService;
import com.htlimst.lieferrex.service.mandant.MandantService;
import com.htlimst.lieferrex.service.security.UserPojo;
import com.htlimst.lieferrex.service.security.UserPrincipalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
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
    public KundeRegistrationDto userRegistrationDto(){
        return new KundeRegistrationDto();
    }


    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") @Valid KundeRegistrationDto registrationDto, BindingResult result){

        UserPojo existingEmail = userPrincipalDetailsService.findUserByEmail(registrationDto.getEmail());
        if (existingEmail != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
            System.out.println("Schon vorhanden");
        }
        if(!registrationDto.isAgb()){
            System.out.println("agb nicht akzeptiert");
            return "redirect:/register?error";
        }
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getField() + " - " + error.getDefaultMessage());
            }
            return "redirect:/register?error";
        }

        kundeService.save(registrationDto);
        return "redirect:/register?success";

    }



    @ModelAttribute("mandant")
    public MandantRegistrationDto mandantRegistrationDto(){
        return new MandantRegistrationDto();
    }

    @GetMapping("/restaurantpartner")
    public String showRestaurantpartner() {
        return "main/restaurantpartner.html";
    }

    @PostMapping("/restaurantpartner")
    public String registerMandantenAccount(@ModelAttribute("mandant") @Valid MandantRegistrationDto registrationDto, BindingResult result){

        UserPojo existingEmail = userPrincipalDetailsService.findUserByEmail(registrationDto.getEmail());
        if (existingEmail != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
            System.out.println("Schon vorhanden");
        }
        if(!registrationDto.isAgb()){
            System.out.println("agb nicht akzeptiert");
            return "redirect:/restaurantpartner?error";
        }
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getField() + " - " + error.getDefaultMessage());
            }
            return "redirect:/restaurantpartner?error";
        }

        mandantService.saveRegistrationDto(registrationDto);
        return "redirect:/restaurantpartner?success";

    }

}
