package com.htlimst.lieferrex.controller.gericht;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.gericht.GerichtService;
import com.htlimst.lieferrex.service.mandant.MandantService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/gerichte")
public class GerichtController {

    private GerichtService gerichtService;
    private MandantService mandantService;
    private AngestellterService angestellterService;

    @Autowired
    public GerichtController(GerichtService gerichtService, MandantService mandantService, AngestellterService angestellterService){
        this.gerichtService = gerichtService;
        this.mandantService = mandantService;
        this.angestellterService = angestellterService;
    }

    @GetMapping
    public String showDashboardGerichte(Model model) {
        // Gerichte mit Status 1 oder 2
        List<Gericht> gerichteList = gerichtService.getGerichtByStatus();
        // Gerichte mit Status 0
        List<Gericht> gerichteListStatusZero = gerichtService.getGerichtByStatusZero();
        model.addAttribute("gerichteList", gerichteList);
        model.addAttribute("gerichteListStatusZero", gerichteListStatusZero);
        model.addAttribute("gericht", new Gericht());
        model.addAttribute("numberDisabled", gerichtService.numberDisabled());
        model.addAttribute("numberActive", gerichtService.numberActive());
        model.addAttribute("numberAll", gerichtService.numberAll());
        return "dashboard/gerichte.html";
    }
    @GetMapping(value="{id}")
    public String showModalGericht(@PathVariable("id") Long id){
        gerichtService.savingStatusGericht(id);
        return "redirect:/dashboard/gerichte";
    }

    @PostMapping("/save")
    public String saveGericht(Gericht gericht, @RequestParam Optional<String> aktiviert, @RequestParam Optional<String> aktion, @RequestParam Optional<String> addPreisangebot, @AuthenticationPrincipal UserPrincipal principal){
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();
        gericht.setMandant(foundMandant);
        if(!aktiviert.isPresent()){
            gericht.setStatus(0);
        } else if(!aktion.isPresent()){
            gericht.setStatus(1);
        } else {
            gericht.setStatus(2);
        }
        if(!addPreisangebot.isPresent()){
            gericht.setPreisangebot(0.0);
        }
        gerichtService.save(gericht);
        return "redirect:/dashboard/gerichte";
    }



}