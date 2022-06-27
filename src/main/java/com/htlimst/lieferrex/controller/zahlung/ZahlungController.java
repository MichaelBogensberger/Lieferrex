package com.htlimst.lieferrex.controller.zahlung;

import com.htlimst.lieferrex.controller.zubereitung.BestellungModel;
import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.model.enums.BestellartEnum;
import com.htlimst.lieferrex.model.enums.BestellstatusEnum;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.bestellung.BestellungService;
import com.htlimst.lieferrex.service.overview.OverviewService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import com.htlimst.lieferrex.service.zahlung.ZahlungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/dashboard/zahlungen")
public class ZahlungController {

    private ZahlungService zahlungService;
    private AngestellterService angestellterService;

    @Autowired
    public ZahlungController(ZahlungService zahlungService, AngestellterService angestellterService) {
        this.zahlungService = zahlungService;
        this.angestellterService = angestellterService;
    }

    @GetMapping
    public String seitenAufruf(@AuthenticationPrincipal UserPrincipal principal, Model model){
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();

        List<Bestellung> alleBestellungenList = zahlungService.alleBestellungen(foundMandant.getId());
        List<Umsatz> alleUmsaetze = zahlungService.getUmsatzByMandant(foundMandant);


        double jahresUmsatz = 0.0;
        LocalDateTime jahresZeit = null;
        LocalDateTime monatsZeit = null;

        int zahlungen = alleBestellungenList.size();
        double proBestellung = 0.0;
        double diesenMonat = 0.0;
        double umsatzImMonat = 0.0;
        int anzahlAnOrdersImMonat = 0;
        double durschnittProBestellungImMonat = 0;
        double umsatzImJahr = 0.0;
        int anzahlAnOrdersImJahr = 0;
        double durchschnittProBestellungImJahr = 0;
        List<Bestellung> letztenDreiBestellungen = zahlungService.letztenDreiBestellungne(foundMandant.getId());
        List<KundeUndDatumModel> kunde = new ArrayList<>();

        for (Bestellung bestellung : letztenDreiBestellungen){
            kunde.add(new KundeUndDatumModel(bestellung, bestellung.getBestelldatum().toLocalDateTime().getDayOfMonth() + "." + bestellung.getBestelldatum().toLocalDateTime().getMonthValue() + "." + bestellung.getBestelldatum().toLocalDateTime().getYear()));
        }

        for (Bestellung bestellung : alleBestellungenList){
            monatsZeit = bestellung.getBestelldatum().toLocalDateTime();
            jahresZeit = bestellung.getBestelldatum().toLocalDateTime();
            // Monatsberechnung
            if(LocalDateTime.now().getMonthValue() == monatsZeit.getMonthValue()){
                diesenMonat += bestellung.getGesamtpreis();
                umsatzImMonat = diesenMonat;
                anzahlAnOrdersImMonat++;
            }
            // Jahresberechnung
            if(LocalDateTime.now().getYear() == jahresZeit.getYear()){
                jahresUmsatz += bestellung.getGesamtpreis();
                umsatzImJahr = jahresUmsatz;
                anzahlAnOrdersImJahr++;
            }
            // Einnahmen Pro bestellung
            proBestellung += bestellung.getGesamtpreis();

        }
        proBestellung = proBestellung/(alleBestellungenList.size());
        durschnittProBestellungImMonat = diesenMonat / anzahlAnOrdersImMonat;
        durchschnittProBestellungImJahr = jahresUmsatz / anzahlAnOrdersImMonat;


        ZahlungModel zahlungModel = new ZahlungModel(zahlungen, proBestellung, diesenMonat, umsatzImMonat, anzahlAnOrdersImMonat, durschnittProBestellungImMonat, umsatzImJahr, anzahlAnOrdersImJahr, durchschnittProBestellungImJahr, letztenDreiBestellungen, kunde);

        model.addAttribute("zahlung" ,zahlungModel);
        return "/dashboard/zahlungen.html";
    }

}