package com.htlimst.lieferrex.controller.mandant;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.repository.AngestellterRepository;
import com.htlimst.lieferrex.security.AccessValidation;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.angestellter.AngestellterServiceImpl;
import com.htlimst.lieferrex.service.mandant.MandantService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class MandantController {

    @Autowired
    private MandantService mandantService;

    @Autowired
    private AccessValidation accessValidation;

    @Autowired
    private AngestellterServiceImpl angestellterService;


    @GetMapping("/dashboard/mandant")
    public String showDashboardMandant(Model model) {

        Mandant mandant = mandantService.listAll();
        model.addAttribute("mandant", mandant);
        return "dashboard/mandant.html";
    }

    @GetMapping("/dashboard/m")
    public String test(@AuthenticationPrincipal UserPrincipal principal, Model model) {
        boolean isKunde = accessValidation.isKunde(principal);

        if(!isKunde) {
           Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
           model.addAttribute("angestellter", foundAngestellter);
        }






        return "dashboard/dashboard.html";
    }




}
