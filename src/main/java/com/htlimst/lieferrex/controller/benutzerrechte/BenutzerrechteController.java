package com.htlimst.lieferrex.controller.benutzerrechte;


import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.repository.RolleRepository;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/dashboard/benutzer")
public class BenutzerrechteController {

    private AngestellterService angestellterService;
    private RolleRepository rolleRepository;

    @Autowired
    public BenutzerrechteController(AngestellterService angestellterService, RolleRepository rolleRepository){
        this.angestellterService = angestellterService;
        this.rolleRepository = rolleRepository;
    }

    @GetMapping
    public String showAllAngestellte(Model model){
        List<Angestellter> angestellterList = angestellterService.alleAngestellen();
        model.addAttribute("angestellterList", angestellterList);
        model.addAttribute("angestellter", new Angestellter());
        model.addAttribute("anzahl", angestellterService.countAngestellter());
        model.addAttribute("admins", angestellterService.countAdmins());
        model.addAttribute("angestellte", angestellterService.countAngestellter());
        return "dashboard/benutzer.html";
    }
    @PostMapping("/save")
    public String saveAngestellter(Angestellter angestellter, @RequestParam String rollen, @AuthenticationPrincipal UserPrincipal principal){
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();
        angestellterService.saveAngestellter(angestellter, rollen, foundMandant);
        return "redirect:/dashboard/benutzer";
    }
    @PostMapping("/create")
    public String createAngestellter(Angestellter angestellter, @RequestParam String rollen, @AuthenticationPrincipal UserPrincipal principal){
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();
        angestellterService.saveAngestellter(angestellter, rollen, foundMandant);
        return "redirect:/dashboard/benutzer";
    }
}
