package com.htlimst.lieferrex.controller;

import com.htlimst.lieferrex.dto.KundeRegistrationDto;
import com.htlimst.lieferrex.service.kunde.KundeService;
import com.htlimst.lieferrex.service.security.UserPojo;
import com.htlimst.lieferrex.service.security.UserPrincipalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginRegistrationController {

    public KundeService kundeService;
    public UserPrincipalDetailsService userPrincipalDetailsService;

    @Autowired
    public LoginRegistrationController(KundeService kundeService, UserPrincipalDetailsService userPrincipalDetailsService) {
        this.kundeService = kundeService;
        this.userPrincipalDetailsService = userPrincipalDetailsService;
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

        if (result.hasErrors()) {
            return "registration";
        }
        System.out.println("Registrieren");
        kundeService.save(registrationDto);
        return "redirect:/registration?success";
    }

}
