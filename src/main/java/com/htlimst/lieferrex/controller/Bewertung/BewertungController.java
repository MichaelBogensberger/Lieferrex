package com.htlimst.lieferrex.controller.Bewertung;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.bewertung.BewertungService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.expression.Dates;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Controller
@RequestMapping("/dashboard/bewertungen")
public class BewertungController {

    private AngestellterService angestellterService;
    private BewertungService bewertungService;

    @Autowired
    public BewertungController(AngestellterService angestellterService, BewertungService bewertungService) {
        this.angestellterService = angestellterService;
        this.bewertungService = bewertungService;
    }

    @GetMapping
    public String showPage(@AuthenticationPrincipal UserPrincipal principal, Model model){
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();
        List<Bestellung> alleBestellungenMitNull = bewertungService.alleBewertungenByMandant(foundMandant);
        List<Bestellung> alleBestellungen = new ArrayList<>();
        List<Bestellung> bestellungen = new ArrayList<>();

        Map<Integer, Integer> bewertungAnzahl = new HashMap<>();
        Map<Integer, Double> valueNow = new HashMap<>();

        for (Bestellung bestellung : alleBestellungenMitNull){
            if(bestellung.getBewertung() != null){
                alleBestellungen.add(bestellung);
            } else {
                bestellungen.add(bestellung);
            }
        }

        double durchschnittBewertung = 0.0;
        Integer[] numArray = new Integer[alleBestellungen.size()];
        int counter = 0;

        for (int i = 1; i <= 5; i++) {
            bewertungAnzahl.put(i,0);
            valueNow.put(i,0.0);
        }


        for (Bestellung bestellung : alleBestellungen){
            durchschnittBewertung += bestellung.getBewertung().intValue();
            numArray[counter] = bestellung.getBewertung().intValue();
            counter++;

            if(!bewertungAnzahl.containsKey(bestellung.getBewertung())){
                bewertungAnzahl.put(bestellung.getBewertung(), 1);
            } else {
                bewertungAnzahl.put(bestellung.getBewertung(), bewertungAnzahl.get(bestellung.getBewertung()) + 1);
                valueNow.put(bestellung.getBewertung(), valueNow.get(bestellung.getBewertung()) + 1);
            }

        }

        Integer sum = 0;

        double median = 0.0;
        if (numArray.length != 0 && numArray.length % 2 == 0){
            median = ((double)numArray[numArray.length/2] + (double)numArray[numArray.length/2 - 1])/2;
        }else if (numArray.length != 0 && numArray.length % 2 == 1){
            median = (double) numArray[numArray.length/2];
        }


        for (Map.Entry<Integer, Integer> entry : bewertungAnzahl.entrySet()){
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            sum += entry.getValue();
        }

        for (Map.Entry<Integer, Double> entry : valueNow.entrySet()){
            if(entry.getValue() != 0){
                BigDecimal proBestellungBD = new BigDecimal((entry.getValue()/sum) *100 ).setScale(2, RoundingMode.HALF_UP);
                entry.setValue(proBestellungBD.doubleValue());
            }
        }

        if(durchschnittBewertung != 0.0){
            durchschnittBewertung = durchschnittBewertung / alleBestellungen.size();
        }


        List<BewertungUndDatumModel> bewertungUndDatumModels = new ArrayList<>();

        for (Bestellung bestellung: alleBestellungen){
            bewertungUndDatumModels.add(new BewertungUndDatumModel(bestellung, String.valueOf(bestellung.getBestelldatum().toLocalDateTime().getDayOfMonth()) + '.' + String.valueOf(bestellung.getBestelldatum().toLocalDateTime().getMonthValue()) + '.' + String.valueOf(bestellung.getBestelldatum().toLocalDateTime().getYear()), String.valueOf(bestellung.getBestelldatum().toLocalDateTime().getHour()) + ':' + String.valueOf(bestellung.getBestelldatum().toLocalDateTime().getMinute())
                ));
        }

        model.addAttribute("median", median);
        model.addAttribute("durchschnitt", durchschnittBewertung);
        model.addAttribute("alleBewertungen", bewertungUndDatumModels);
        model.addAttribute("bewertungen", alleBestellungen.size());
        model.addAttribute("bewertungMap", bewertungAnzahl);
        model.addAttribute("valueNow", valueNow);
        return "dashboard/bewertungen.html";
    }


}
