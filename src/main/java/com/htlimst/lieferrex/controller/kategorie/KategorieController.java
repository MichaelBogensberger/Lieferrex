package com.htlimst.lieferrex.controller.kategorie;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Kategorie;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.enums.KategorieEnum;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.mandant.MandantService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/kategorien")
public class KategorieController {

    public AngestellterService angestellterService;
    public MandantService mandantService;

    @Autowired
    public KategorieController(AngestellterService angestellterService, MandantService mandantService) {
        this.angestellterService = angestellterService;
        this.mandantService = mandantService;
    }

    @GetMapping
    public String showDashboardKategorien(@AuthenticationPrincipal UserPrincipal principal, Model model) {
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();

        KategorieModel kategorieModel = new KategorieModel();

        if (foundMandant.getKategorie() == null){
            kategorieModel.setB1(KategorieEnum.OTHER);
        } else {
            kategorieModel.setB1(foundMandant.getKategorie().getName());
        }

        model.addAttribute("kategorie",kategorieModel.getB1().toString());
        model.addAttribute("user", foundAngestellter.getVorname() + ' ' + foundAngestellter.getNachname());
        model.addAttribute("vname", foundAngestellter.getVorname());
        model.addAttribute("nname", foundAngestellter.getNachname());
        model.addAttribute("firmenname", foundMandant.getFirmenname());
        return "dashboard/kategorien.html";
    }

    @PostMapping("/save")
    public String savingKategorie(@AuthenticationPrincipal UserPrincipal principal, @RequestParam(name = "radio-set-1") Optional<KategorieEnum> kategorie){
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();

        if(kategorie.isPresent() && kategorie != null){
            foundMandant.getKategorie().setName(kategorie.get());
            mandantService.save(foundMandant);
        }


        return "redirect:./";
    }
}
