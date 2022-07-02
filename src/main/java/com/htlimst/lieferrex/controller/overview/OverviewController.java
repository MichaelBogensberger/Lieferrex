package com.htlimst.lieferrex.controller.overview;

import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.model.enums.BestellartEnum;
import com.htlimst.lieferrex.model.enums.BestellstatusEnum;
import com.htlimst.lieferrex.repository.BestellungRepository;
import com.htlimst.lieferrex.repository.SeitenaufrufeRepository;
import com.htlimst.lieferrex.repository.UmsatzRepository;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.bestellung.BestellungService;
import com.htlimst.lieferrex.service.bestellung.BestellungServiceImpl;
import com.htlimst.lieferrex.service.gericht.GerichtService;
import com.htlimst.lieferrex.service.mandant.MandantService;
import com.htlimst.lieferrex.service.overview.OverviewService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/dashboard")
public class OverviewController {

    public OverviewService overviewService;
    public AngestellterService angestellterService;
    public BestellungService bestellungService;

    @Autowired
    public OverviewController(OverviewService overviewService, AngestellterService angestellterService,
            BestellungService bestellungService) {
        this.overviewService = overviewService;
        this.angestellterService = angestellterService;
        this.bestellungService = bestellungService;
    }

    @Autowired
    SeitenaufrufeRepository seitenaufrufeRepository;

    @GetMapping
    public String seitenAufruf(@AuthenticationPrincipal UserPrincipal principal, Model model,
            HttpServletResponse response) {
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        Optional<Seitenaufrufe> seitenaufrufe = seitenaufrufeRepository
                .getSeitenaufrufeByMandantAndJahrAndMonat(foundMandant, year, month);
        int aufrufe;

        if (seitenaufrufe.isPresent()) {
            aufrufe = seitenaufrufe.get().getAufrufe();
        } else {
            seitenaufrufeRepository.save(new Seitenaufrufe(null, month, year, 0, foundMandant));
            aufrufe = 0;
        }

        long verkaufteGerichte = overviewService.getVerkaufteGerichte(foundMandant.getId());

        List<Umsatz> alleUmsaetzeByMandant = overviewService.alleUmsaetzeByMandant(foundMandant);
        double umsatzSumme = 0.0;

        if (overviewService.checkIfUmsatzImMonatVorhanden(foundMandant)) {
            for (Umsatz umsatz : alleUmsaetzeByMandant) {
                if (umsatz.getMonat() == LocalDateTime.now().getMonthValue()) {
                    umsatzSumme += umsatz.getUmsatz();
                }
            }
        }

        model.addAttribute("seitenaufrufe", aufrufe);
        model.addAttribute("umsatz", umsatzSumme);
        model.addAttribute("gerichteVerkauft", verkaufteGerichte);
        model.addAttribute("user", foundAngestellter.getVorname() + ' ' + foundAngestellter.getNachname());
        model.addAttribute("vname", foundAngestellter.getVorname());
        model.addAttribute("nname", foundAngestellter.getNachname());
        model.addAttribute("firmenname", foundMandant.getFirmenname());

        // -----------------------
        // Token in Daterbank und Cookie fuer REST.
        // -----------------------
        String token = UUID.randomUUID().toString();
        response.addCookie(new Cookie("token", token));
        foundAngestellter.setToken(token);
        angestellterService.saveAngestellter(foundAngestellter);
        List<Bestellung> latestBestellungList = overviewService.getLatestBestellungen(foundMandant.getId());
        model.addAttribute("latestBestellungen", latestBestellungList);
        model.addAttribute("popular", overviewService.getPopularBestellungen(foundMandant.getId()));
        return "dashboard/dashboard.html";
    }

}