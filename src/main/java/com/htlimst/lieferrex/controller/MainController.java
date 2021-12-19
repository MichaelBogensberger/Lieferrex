package com.htlimst.lieferrex.controller;

import java.util.HashMap;

import com.htlimst.lieferrex.service.position.PositionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private PositionService serv;

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

    @GetMapping("/restaurant")
    public String Template(Model model) {
        // Restaurant Name
        // Get template
        // Get Fragments and Position
        // Build HashMap
        // Return template


        HashMap<String, String> map = new HashMap<>();
        map.put("name", "afasf");
        map.put("title", serv.getById(2L).getPosition());
        map.put("beschreibung", "Ein ganz tolles Restaurant.");
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
