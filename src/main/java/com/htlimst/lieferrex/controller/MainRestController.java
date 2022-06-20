package com.htlimst.lieferrex.controller;

import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.service.googleApi.PlacesApi;
import com.htlimst.lieferrex.service.mandant.MandantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class MainRestController {

    private MandantService mandantService;
    private PlacesApi placesApi;

    @Autowired
    public MainRestController(MandantService mandantService, PlacesApi placesApi) {
        this.mandantService = mandantService;
        this.placesApi = placesApi;
    }



    @GetMapping("/seach/restaurant/{inputAdresse}")
    public List<String> saveModule(@PathVariable String inputAdresse) {

        List<String> addressen = null;
        try {
            addressen = placesApi.getPlaces(inputAdresse);
        } catch (AdresseNotFoundException adresseNotFoundException) {
            System.out.println(adresseNotFoundException.getMessage());
        }
        return addressen;
    }
}
