package com.htlimst.lieferrex.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.htlimst.lieferrex.dto.MandantSuchDto;
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
import com.htlimst.lieferrex.service.mandant.MandantServiceImpl;
import com.htlimst.lieferrex.service.mandant.MandantService;

import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private GerichtService gerichtService;

    public MainController(MandantRepository mandantRepository, MandantService mandantService, FragmentServiceImpl fragmentServiceImpl, FragmentTextServiceImpl fragmenttextServiceImpl, FragmentMapServiceImpl fragmentmapServiceImpl) {
        this.mandantRepository = mandantRepository;
        this.mandantService = mandantService;
        this.fragmentServiceImpl = fragmentServiceImpl;
        this.fragmenttextServiceImpl = fragmenttextServiceImpl;
        this.fragmentmapServiceImpl = fragmentmapServiceImpl;
    }


    @GetMapping("")
    public String showIndexPage() {
        System.out.println("Index page loaded");

        return "main/index";
    }

    @GetMapping("/search")
    public String showSearchPage(@RequestParam(value = "search", required = false) String search, Model model) {
        System.out.println(search);
        try {
            List<MandantSuchDto> mandanten = mandantService.findMandantByOrt(search);
            model.addAttribute("mandanten", mandanten);
            return "main/search";
        }catch (MandantNotFoundException mandantNotFoundException){
            System.out.println(mandantNotFoundException.getMessage());
            model.addAttribute("error", "Es wurden keine Restaurants in der Umgebung gefunden!");
            return "redirect:/";
        }

    }

    @GetMapping("/orders")
    public String showOrdersPage() {
        return "main/orders";
    }






    @GetMapping("/buildOne")
    public String showBaukastenPage() {
        return "baukasten/one.html";
    }

    @GetMapping("/dashboard/bestellungen")
    public String showDashboardBestellungen() {
        return "dashboard/bestellungen.html";
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
