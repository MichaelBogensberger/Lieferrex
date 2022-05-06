package com.htlimst.lieferrex.controller.overview;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Seitenaufrufe;
import com.htlimst.lieferrex.model.Umsatz;
import com.htlimst.lieferrex.repository.SeitenaufrufeRepository;
import com.htlimst.lieferrex.repository.UmsatzRepository;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.gericht.GerichtService;
import com.htlimst.lieferrex.service.mandant.MandantService;
import com.htlimst.lieferrex.service.overview.OverviewService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
public class OverviewController {

    public OverviewService overviewService;
    public AngestellterService angestellterService;

    @Autowired
    public OverviewController(OverviewService overviewService, AngestellterService angestellterService) {
        this.overviewService = overviewService;
        this.angestellterService = angestellterService;
    }

    @GetMapping
    public String seitenAufruf(@AuthenticationPrincipal UserPrincipal principal, Model model){
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();
        Seitenaufrufe seitenaufrufe = overviewService.seitenaufrufe(foundMandant);
        Umsatz umsatz = overviewService.umsatz(foundMandant);
        long verkaufteGerichte = overviewService.getVerkaufteGerichte(foundMandant.getId());
        model.addAttribute("seitenaufrufe", seitenaufrufe.getAufrufe());
        model.addAttribute("umsatz", umsatz.getUmsatz());
        model.addAttribute("gerichteVerkauft", verkaufteGerichte);
        return "dashboard/dashboard.html";
    }

}