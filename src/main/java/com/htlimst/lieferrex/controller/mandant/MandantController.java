package com.htlimst.lieferrex.controller.mandant;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.service.Util;
import com.htlimst.lieferrex.service.angestellter.AngestellterServiceImpl;
import com.htlimst.lieferrex.service.mandant.MandantService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MandantController {

    @Autowired
    private MandantService mandantService;

    @Autowired
    private AngestellterServiceImpl angestellterService;


    @GetMapping("/dashboard/mandant")
    public String showDashboardMandant(Model model, @AuthenticationPrincipal UserPrincipal principal) {

        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        model.addAttribute("angestellter", foundAngestellter);
        model.addAttribute("mandant", foundAngestellter.getMandant());

        return "dashboard/mandant.html";
    }

    @PostMapping("/dashboard/mandant/save")
    public String saveUser(Mandant mandant, RedirectAttributes ra, @AuthenticationPrincipal UserPrincipal principal) {
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();

        mandant.setId(foundMandant.getId());
        mandant.setLayout(foundMandant.getLayout());
        mandant.setSeitenaufrufe_summe(foundMandant.getSeitenaufrufe_summe());
        mandant.setUmsatz_summe(foundMandant.getUmsatz_summe());


        mandantService.save(mandant);

        ra.addFlashAttribute("message", "Einstellungen wurden geändert.");
        return "redirect:/dashboard/mandant";
    }




}
