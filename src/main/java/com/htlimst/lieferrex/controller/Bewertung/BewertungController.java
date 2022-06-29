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

import java.lang.reflect.Array;
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

        List<Bestellung> alleBestellungen = bewertungService.alleBewertungenByMandant(foundMandant);
        double durchschnittBewertung = 0.0;
        int[] numArray = new int[alleBestellungen.size()];
        int counter = 0;
        for (Bestellung bestellung : alleBestellungen){
            durchschnittBewertung += bestellung.getBewertung();
            numArray[counter] = bestellung.getBewertung();
            counter++;
        }

        if(durchschnittBewertung != 0.0){
            durchschnittBewertung = durchschnittBewertung / alleBestellungen.size();
        }



        double median = 0.0;
        if (numArray.length % 2 == 0){
            median = ((double)numArray[numArray.length/2] + (double)numArray[numArray.length/2 - 1])/2;
        }else{
            median = (double) numArray[numArray.length/2];
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
        return "dashboard/bewertungen.html";
    }


}
