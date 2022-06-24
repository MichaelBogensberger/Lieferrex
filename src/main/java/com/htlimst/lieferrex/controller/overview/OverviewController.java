package com.htlimst.lieferrex.controller.overview;

import com.htlimst.lieferrex.model.*;
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
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
    public String seitenAufruf(@AuthenticationPrincipal UserPrincipal principal, Model model, HttpServletResponse response){
        System.out.println("load dashboard");
        
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();
        System.out.println(foundMandant.getEmail());
        Seitenaufrufe seitenaufrufe = overviewService.seitenaufrufe(foundMandant);
        Umsatz umsatz = overviewService.umsatz(foundMandant);
        long verkaufteGerichte = overviewService.getVerkaufteGerichte(foundMandant.getId());
        model.addAttribute("seitenaufrufe", seitenaufrufe.getAufrufe());
        model.addAttribute("umsatz", umsatz.getUmsatz());
        model.addAttribute("gerichteVerkauft", verkaufteGerichte);


        // -----------------------
        // Token in Daterbank und Cookie fuer REST.
        // -----------------------
        String token = UUID.randomUUID().toString();
        response.addCookie(new Cookie("token", token));
        foundAngestellter.setToken(token);
        angestellterService.saveAngestellter(foundAngestellter);
        List<Bestellung> latestBestellungList = overviewService.getLatestBestellungen(foundMandant.getId());
        model.addAttribute("latestBestellungen", latestBestellungList);
        model.addAttribute("popular", overviewService.anzahlGeakuft(foundMandant.getId()));
        return "dashboard/dashboard.html";
    }

}