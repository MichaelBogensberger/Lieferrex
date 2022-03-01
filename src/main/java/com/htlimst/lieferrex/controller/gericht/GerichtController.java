package com.htlimst.lieferrex.controller.gericht;

import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.service.gericht.GerichtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        // Gerichte mit Status 1 oder 2
        List<Gericht> gerichteList = gerichtService.getGerichtByStatus();
        // Gerichte mit Status 0
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