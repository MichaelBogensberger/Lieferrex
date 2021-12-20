package com.htlimst.lieferrex.controller;

import java.util.HashMap;
import java.util.Set;

import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.service.fragment.FragmentService;
import com.htlimst.lieferrex.service.position.PositionService;
import com.htlimst.lieferrex.service.template.TemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private FragmentService fragmentService;

    @Autowired
    private TemplateService templateService;

    // Testweise, ausgetauscht mit Serv
    @Autowired
    private MandantRepository mandantRepository;

    @GetMapping("")
    public String showIndexPage() {
        System.out.println("Index page loaded");

        return "main/index";
    }

    @GetMapping("/search")
    public String showSearchPage() {
        return "main/search";
    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "main/login";
    }


    @GetMapping("/register")
    public String showRegisterPage() {
        return "main/register";
    }


    @GetMapping("/buildOne")
    public String showBaukastenPage() {
        return "baukasten/one.html";
    }

    @GetMapping("/restaurant/{restaurant}")
    public String Template(Model model, @PathVariable String restaurant) {
        // X Restaurant Name
        // X Get template
        // Get Fragments and Position
        // Build HashMap
        // Return template

        Mandant mandant = mandantRepository.findMandantByFirmenname(restaurant).get();
        Set<Fragment> fragments = fragmentService.getAllFragmentsByMandant(mandant);

        for (Fragment fragment : fragments) {
            System.out.println(fragment.getTitle());
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("name", "afasf");
        map.put("title", restaurant);
        map.put("beschreibung", mandant.getTemplate().getTemplate());
        map.put("img", "https://via.placeholder.com/200");
        model.addAttribute("one", map);
        return "baukasten/template.html";
    }


    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard/dashboard.html";
    }

    @GetMapping("/dashboard/bestellungen")
    public String showDashboardBestellungen() {
        return "dashboard/bestellungen.html";
    }



}
