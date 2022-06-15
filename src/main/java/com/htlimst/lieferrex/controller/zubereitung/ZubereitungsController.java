package com.htlimst.lieferrex.controller.zubereitung;

import com.htlimst.lieferrex.model.*;
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
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
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

        Timestamp timestamp = null;
        String zusatzinfo = "";

        for (Bestellung bestellung:alleBestellungen) {
          //  gerichtBestellungList.add(bestellung.getGerichteBestellungen());
            timestamp = bestellung.getBestelldatum();
            HashMap<String, Integer> gerichtBestellungModelList = new HashMap<>();
            for(GerichtBestellung gerichtBestellung : bestellung.getGerichteBestellungen())
            {
                if(!gerichtBestellungModelList.containsKey(gerichtBestellung.getGericht().getName()))
                {
                    gerichtBestellungModelList.put(gerichtBestellung.getGericht().getName(),1);
                } else
                {
                    gerichtBestellungModelList.put(gerichtBestellung.getGericht().getName(),gerichtBestellungModelList.get(gerichtBestellung.getGericht().getName())+1);
                }
                zusatzinfo += gerichtBestellung.getAnmerkung() + ", ";
            }

            modeldata.add(new BestellungModel(gerichtBestellungModelList,timestamp,zusatzinfo));
        }

        System.out.println(modeldata);
        System.out.println(modeldata.get(0).getZusatinfos());

        model.addAttribute("gerichteBestellungsDetail", modeldata);
        return "dashboard/bestellungen.html";
    }

}

/*
    Bestellungen holen -> von jeder bestellung die Gerichte in der zwischendabelle solven -> und zum schluss gerichte holen

 */

class GerichtPOJO {
    String gereichtName;
    Integer gerichtrCount;

    public GerichtPOJO(String gereichtName, Integer gerichtrCount) {
        this.gereichtName = gereichtName;
        this.gerichtrCount = gerichtrCount;
    }
}
