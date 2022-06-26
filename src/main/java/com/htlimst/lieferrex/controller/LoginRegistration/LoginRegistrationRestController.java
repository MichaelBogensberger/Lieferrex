package com.htlimst.lieferrex.controller.LoginRegistration;


import com.htlimst.lieferrex.dto.AdressDto;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import com.htlimst.lieferrex.service.googleApi.PlacesApi;
import com.htlimst.lieferrex.service.mandant.MandantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class LoginRegistrationRestController {


    private PlacesApi placesApi;

    @Autowired
    public LoginRegistrationRestController(MandantService mandantService, PlacesApi placesApi) {
        this.placesApi = placesApi;
    }


    @GetMapping("/seach/adresse/{inputAdresse}")
    public List<AdressDto> getAdressen(@PathVariable String inputAdresse) {

        List<AdressDto> addressen = null;
        try {
            addressen = placesApi.getAdressen(inputAdresse);
        } catch (AdresseNotFoundException adresseNotFoundException) {
            System.out.println(adresseNotFoundException.getMessage());
        }
        return addressen;
    }


}
