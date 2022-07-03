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
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.function.DoubleBinaryOperator;

@Controller
@RequestMapping("/dashboard/zahlungen")
public class ZahlungController {

    private ZahlungService zahlungService;
    private AngestellterService angestellterService;
    private OverviewService overviewService;

    @Autowired
    public ZahlungController(ZahlungService zahlungService, AngestellterService angestellterService, OverviewService overviewService) {
        this.zahlungService = zahlungService;
        this.angestellterService = angestellterService;
        this.overviewService = overviewService;
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

        List<Umsatz> umsatzList = overviewService.alleUmsaetzeByMandant(foundMandant);

        for (Bestellung bestellung : letztenDreiBestellungen){
            kunde.add(new KundeUndDatumModel(bestellung, bestellung.getBestelldatum().toLocalDateTime().getDayOfMonth() + "." + bestellung.getBestelldatum().toLocalDateTime().getMonthValue() + "." + bestellung.getBestelldatum().toLocalDateTime().getYear()));
        }

        if (overviewService.checkIfUmsatzImMonatVorhanden(foundMandant)){
            for (Umsatz umsatz : umsatzList){
                if (LocalDateTime.now().getMonthValue() == umsatz.getMonat() && LocalDateTime.now().getYear() == umsatz.getJahr()){
                    diesenMonat += umsatz.getUmsatz();
                }
                if (LocalDateTime.now().getYear() == umsatz.getJahr()){
                    jahresUmsatz += umsatz.getUmsatz();
                }
            }
        }

        for (Bestellung bestellung : alleBestellungenList){
            monatsZeit = bestellung.getBestelldatum().toLocalDateTime();
            jahresZeit = bestellung.getBestelldatum().toLocalDateTime();
            // Monatsberechnung
            if(LocalDateTime.now().getMonthValue() == monatsZeit.getMonthValue() && LocalDateTime.now().getYear() == jahresZeit.getYear()){
                umsatzImMonat = bestellung.getGesamtpreis();
                anzahlAnOrdersImMonat++;
            }
            // Jahresberechnung
            if(LocalDateTime.now().getYear() == jahresZeit.getYear()){
                umsatzImJahr = bestellung.getGesamtpreis();;
                anzahlAnOrdersImJahr++;
            }
            // Einnahmen Pro bestellung
            proBestellung += bestellung.getGesamtpreis();

        }

        if(diesenMonat != 0.0 && jahresUmsatz != 0.0){
            proBestellung = proBestellung/(alleBestellungenList.size());
            durschnittProBestellungImMonat = diesenMonat / anzahlAnOrdersImMonat;
            durchschnittProBestellungImJahr = jahresUmsatz / anzahlAnOrdersImJahr;

            if(anzahlAnOrdersImMonat != 0 && anzahlAnOrdersImJahr != 0){
                BigDecimal proBestellungBD = new BigDecimal(proBestellung).setScale(2, RoundingMode.HALF_UP);
                BigDecimal durschnittProBestellungImMonatBD = new BigDecimal(durschnittProBestellungImMonat).setScale(2, RoundingMode.HALF_UP);
                BigDecimal durchschnittProBestellungImJahrBD = new BigDecimal(durchschnittProBestellungImJahr).setScale(2, RoundingMode.HALF_UP);

                proBestellung = proBestellungBD.doubleValue();
                durschnittProBestellungImMonat = durschnittProBestellungImMonatBD.doubleValue();
                durchschnittProBestellungImJahr = durchschnittProBestellungImJahrBD.doubleValue();
            } else {
                durchschnittProBestellungImJahr = 0.0;
                durschnittProBestellungImMonat = 0.0;
            }
        }

        ZahlungModel zahlungModel = new ZahlungModel(zahlungen, proBestellung, diesenMonat, umsatzImMonat, anzahlAnOrdersImMonat, durschnittProBestellungImMonat, jahresUmsatz, anzahlAnOrdersImJahr, durchschnittProBestellungImJahr, letztenDreiBestellungen, kunde);

        List<String> datumUmstaz = new ArrayList<>();
        List<Double> valueUmsatz = new ArrayList<>();

        for (Umsatz umsatz : alleUmsaetze){
            if(LocalDateTime.now().getYear() == umsatz.getJahr()){
                datumUmstaz.add(LocalDateTime.of(umsatz.getJahr(), umsatz.getMonat(), 1, 0, 0).getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN));
                valueUmsatz.add(umsatz.getUmsatz());
            }
        }

        model.addAttribute("datumUmsatz", datumUmstaz);
        model.addAttribute("valueUmsatz", valueUmsatz);

        Map<String, Integer> bestellungenImJahr = new HashedMap();

        for (Bestellung bestellung: alleBestellungenList){
            if(LocalDateTime.now().getYear() == bestellung.getBestelldatum().toLocalDateTime().getYear()){
                if(!bestellungenImJahr.containsKey(bestellung.getBestelldatum().toLocalDateTime().getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN))){
                    bestellungenImJahr.put(bestellung.getBestelldatum().toLocalDateTime().getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN), 1);
                } else {
                    bestellungenImJahr.put(bestellung.getBestelldatum().toLocalDateTime().getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN), bestellungenImJahr.get(bestellung.getBestelldatum().toLocalDateTime().getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN))+1);
                }
            }
        }

        model.addAttribute("bestellungenImJahr", bestellungenImJahr);


        model.addAttribute("umsatzGesamt", foundMandant.getUmsatz_summe());
        model.addAttribute("zahlung" ,zahlungModel);
        model.addAttribute("user", foundAngestellter.getVorname() + ' ' + foundAngestellter.getNachname());
        model.addAttribute("vname", foundAngestellter.getVorname());
        model.addAttribute("nname", foundAngestellter.getNachname());
        model.addAttribute("firmenname", foundMandant.getFirmenname());
        return "dashboard/zahlungen.html";
    }

}