package com.htlimst.lieferrex.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.htlimst.lieferrex.dto.MandantSuchDto;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import com.htlimst.lieferrex.exceptions.MandantNotFoundException;
import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.fragments.FragmentText;
import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.service.fragment.FragmentServiceImpl;
import com.htlimst.lieferrex.service.fragmentmap.FragmentMapServiceImpl;
import com.htlimst.lieferrex.service.fragmenttext.FragmentTextServiceImpl;
import com.htlimst.lieferrex.service.gericht.GerichtService;
import com.htlimst.lieferrex.service.googleApi.GeocodingApi;
import com.htlimst.lieferrex.service.mandant.MandantServiceImpl;
import com.htlimst.lieferrex.service.mandant.MandantService;

import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private MandantRepository mandantRepository;
    private FragmentServiceImpl fragmentServiceImpl;
    private FragmentTextServiceImpl fragmenttextServiceImpl;
    private FragmentMapServiceImpl fragmentmapServiceImpl;
    private MandantService mandantService;
    private GeocodingApi geocodingApi;

    @Autowired
    public MainController(MandantRepository mandantRepository, FragmentServiceImpl fragmentServiceImpl, FragmentTextServiceImpl fragmenttextServiceImpl, FragmentMapServiceImpl fragmentmapServiceImpl, MandantService mandantService, GeocodingApi geocodingApi) {
        this.mandantRepository = mandantRepository;
        this.fragmentServiceImpl = fragmentServiceImpl;
        this.fragmenttextServiceImpl = fragmenttextServiceImpl;
        this.fragmentmapServiceImpl = fragmentmapServiceImpl;
        this.mandantService = mandantService;
        this.geocodingApi = geocodingApi;
    }

    @Value("${google.api.key}")
    String key;


    @GetMapping("")
    public String showIndexPage() {
        System.out.println("Index page loaded");

        System.out.println(key);


        return "main/index";
    }

    @GetMapping("/seach")
    public String search(@RequestParam(value = "search", required = false) String Adresse, Model model) {
        // Adresse zu PLZ umwandeln


        try {
            String plz = geocodingApi.findPlzByAdresse(Adresse);

            return "redirect:restaurants/" + plz;

        } catch (AdresseNotFoundException adresseNotFoundException) {
            System.out.println(adresseNotFoundException.getMessage());
            return "main/index";
        }


    }

    @GetMapping("/restaurants/{plz}")
    public String showRestaurants(@PathVariable String plz, Model model) {
        //Alle Restaurants der PLZ anzeigen
        System.out.println(plz);
        try {
            List<MandantSuchDto> mandanten = mandantService.findMandantByPlz(plz);
            model.addAttribute("mandanten", mandanten);
            return "main/search";
        }catch (MandantNotFoundException mandantNotFoundException){
            System.out.println(mandantNotFoundException.getMessage());
            model.addAttribute("error", "Es wurden keine Restaurants in der Umgebung gefunden!");
            return "main/search";
        }

    }

    @GetMapping("/orders")
    public String showOrdersPage() {
        return "main/orders";
    }

    @GetMapping("/checkout")
    public String showCheckoutPage() {
        return "main/checkout";
    }




    @GetMapping("/buildOne")
    public String showBaukastenPage() {
        return "baukasten/one.html";
    }


    @GetMapping("/dashboard/zustellungen")
    public String showDashboardZustellungen() {
        return "dashboard/zustellung.html";
    }

    @GetMapping("/dashboard/kategorien")
    public String showDashboardKategorien() {
        return "dashboard/kategorien.html";
    }


    @GetMapping("/dashboard/oeffnungszeiten")
    public String showDashboardOeffnungszeiten() {
        return "dashboard/oeffnungszeiten.html";
    }



}
