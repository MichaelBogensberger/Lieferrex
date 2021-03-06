package com.htlimst.lieferrex.controller.benutzerrechte;


import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Rolle;
import com.htlimst.lieferrex.repository.RolleRepository;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

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
    public String showAllAngestellte(@AuthenticationPrincipal UserPrincipal principal, Model model){
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();

        List<Angestellter> angestellterList = angestellterService.alleAngestellen(foundMandant.getId());
        List<BenutzerRolleModel> benutzerRolleModelList = new ArrayList<>();

        for (Angestellter angestellter : angestellterList){
            Iterator itr = angestellter.getRolle().iterator();

            if(itr.hasNext()){
                Rolle rolle = (Rolle) itr.next();
                if(rolle.getRolle().trim().equals("ROLE_ANGESTELLTER")){
                    benutzerRolleModelList.add(new BenutzerRolleModel(angestellter, "Angestellter"));
                } else if (rolle.getRolle().trim().equals("ROLE_MANDANT")){
                    benutzerRolleModelList.add(new BenutzerRolleModel(angestellter, "Mandant"));
                }
            }
        }

        model.addAttribute("angestellterList", benutzerRolleModelList);
        model.addAttribute("angestellter", new Angestellter());
        model.addAttribute("anzahl", angestellterService.countAngestellter(foundMandant.getId()) + angestellterService.countAdmins(foundMandant.getId()));
        model.addAttribute("admins", angestellterService.countAdmins(foundMandant.getId()));
        model.addAttribute("angestellte", angestellterService.countAngestellter(foundMandant.getId()));
        model.addAttribute("user", foundAngestellter.getVorname() + ' ' + foundAngestellter.getNachname());
        model.addAttribute("vname", foundAngestellter.getVorname());
        model.addAttribute("nname", foundAngestellter.getNachname());
        model.addAttribute("firmenname", foundMandant.getFirmenname());
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
        angestellter.setToken(UUID.randomUUID().toString());
        angestellterService.saveAngestellter(angestellter, rollen, foundMandant);
        return "redirect:/dashboard/benutzer";
    }

    @Transactional
    @PostMapping("/delete")
    public String deleteAngestellter(@AuthenticationPrincipal UserPrincipal principal, @RequestParam Optional<Long> deleteButton){
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();

        if (deleteButton.isPresent()){
            Angestellter angestellter = angestellterService.getAngestellterByMandandIdAndId(foundMandant.getId(), deleteButton.get());
            if(angestellter.getRolle().size() != 0){
                angestellter.removeRolle(angestellter.getRolle().stream().findAny().get());
                angestellterService.saveAngestellter(angestellter);
            }

            angestellterService.deleteAngestellter(deleteButton.get());
        }

        return "redirect:/dashboard/benutzer";
    }


}
