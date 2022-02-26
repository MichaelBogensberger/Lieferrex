package com.htlimst.lieferrex.controller;

import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.service.gericht.GerichtService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/gerichte")
public class GerichtController {

    private GerichtService gerichtService;


    public GerichtController(GerichtService gerichtService){
        this.gerichtService = gerichtService;
    }

    @GetMapping
    public String showDashboardGerichte(Model model) {
        List<Gericht> gerichteList = gerichtService.getGerichtByStatus();
        List<Gericht> gerichteListStatusZero = gerichtService.getGerichtByStatusZero();
        model.addAttribute("gerichteList", gerichteList);
        model.addAttribute("gerichteListStatusZero", gerichteListStatusZero);
        return "dashboard/gerichte.html";
    }
    @GetMapping("/{id}")
    public String showModalGericht(@PathVariable("id") Long id, Model model){
        Optional<Gericht> gericht = gerichtService.getGerichtById(id);
        model.addAttribute("gericht", gericht);
        return "edit";
    }

}