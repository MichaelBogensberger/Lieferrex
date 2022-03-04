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




}
