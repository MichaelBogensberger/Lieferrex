package com.htlimst.lieferrex.controller.zubereitung;

import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.model.enums.BestellartEnum;
import com.htlimst.lieferrex.repository.BestellungRepository;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.bestellung.BestellungService;
import com.htlimst.lieferrex.service.bestellung.BestellungServiceImpl;
import com.htlimst.lieferrex.service.overview.OverviewService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard/bestellungen")
public class ZubereitungsController {

    public OverviewService overviewService;
    public AngestellterService angestellterService;
    public BestellungService bestellungService;

    @Autowired
    public ZubereitungsController(OverviewService overviewService, AngestellterService angestellterService, BestellungService bestellungService) {
        this.overviewService = overviewService;
        this.angestellterService = angestellterService;
        this.bestellungService = bestellungService;
    }

    @GetMapping
    public String seitenAufruf(@AuthenticationPrincipal UserPrincipal principal, Model model){
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();

        List<Bestellung> alleBestellungen = bestellungService.alleBestellungen(foundMandant.getId());
        List<BestellungModel> modeldata = new ArrayList<>();
        List<BestellungModel> modeldataLieferung = new ArrayList<>();
        int abholung = 0;
        int lieferung = 0;

        for (Bestellung bestellung:alleBestellungen) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String timestamp = sdf.format(new Date(bestellung.getBestelldatum().getTime()));
            String zusatzinfo = "";
            String timestampAbholung = sdf.format(new Date(bestellung.getBestelldatum().getTime()));
            String zusatzinfoAbholung = "";

            if(bestellung.getBestellart().getBestellart() == BestellartEnum.ABHOLUNG){
                HashMap<String, Integer> gerichtBestellungModelList = new HashMap<>();
                for(GerichtBestellung gerichtBestellung : bestellung.getGerichteBestellungen())
                {
                    if (gerichtBestellung.getAnmerkung() != null){
                        zusatzinfo += gerichtBestellung.getAnmerkung() + ", ";
                    }

                    if(!gerichtBestellungModelList.containsKey(gerichtBestellung.getGericht().getName()))
                    {
                        gerichtBestellungModelList.put(gerichtBestellung.getGericht().getName(),1);
                    } else
                    {
                        gerichtBestellungModelList.put(gerichtBestellung.getGericht().getName(),gerichtBestellungModelList.get(gerichtBestellung.getGericht().getName())+1);
                    }
                }

                if(!zusatzinfo.isEmpty()){
                    zusatzinfo = zusatzinfo.substring(0, zusatzinfo.length()-2);
                }
                abholung += 1;
                modeldata.add(new BestellungModel(gerichtBestellungModelList,timestamp,zusatzinfo,abholung,bestellung.getId()));
            }

            if(bestellung.getBestellart().getBestellart() == BestellartEnum.LIEFERUNG){
                HashMap<String, Integer> gerichtBestellungModelListAbholung = new HashMap<>();
                for(GerichtBestellung gerichtBestellung : bestellung.getGerichteBestellungen())
                {
                    if (gerichtBestellung.getAnmerkung() != null){
                        zusatzinfoAbholung += gerichtBestellung.getAnmerkung() + ", ";
                    }

                    if(!gerichtBestellungModelListAbholung.containsKey(gerichtBestellung.getGericht().getName()))
                    {
                        gerichtBestellungModelListAbholung.put(gerichtBestellung.getGericht().getName(),1);
                    } else
                    {
                        gerichtBestellungModelListAbholung.put(gerichtBestellung.getGericht().getName(),gerichtBestellungModelListAbholung.get(gerichtBestellung.getGericht().getName())+1);
                    }
                }

                if(!zusatzinfoAbholung.isEmpty()){
                    zusatzinfoAbholung = zusatzinfoAbholung.substring(0, zusatzinfoAbholung.length()-2);
                }
                lieferung += 1;
                modeldataLieferung.add(new BestellungModel(gerichtBestellungModelListAbholung, timestampAbholung, zusatzinfoAbholung, lieferung, bestellung.getId()));
            }


        }
        model.addAttribute("gerichteBestellungsDetail", modeldata);
        model.addAttribute("gerichteBestellungsDetailLieferung", modeldataLieferung);
        return "dashboard/bestellungen.html";
    }

    @PostMapping
    public String checkingLieferungAndAbholung(@AuthenticationPrincipal UserPrincipal principal, Model model){
        seitenAufruf(principal, model);



        return "dashboard/bestellungen.html";
    }

}