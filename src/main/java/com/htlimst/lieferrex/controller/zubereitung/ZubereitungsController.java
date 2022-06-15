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

        List<GerichtBestellung> gerichtBestellungs = overviewService.gerichtBestellungDetails(foundMandant.getId());
        model.addAttribute("gerichteDetails", gerichtBestellungs);

        List<String> names = new ArrayList<>();
        int anzahlDerBestellungen = 0;
        List<Timestamp> timestamps = new ArrayList<>();

        for (int i = 0; i < gerichtBestellungs.size(); i++) {
            names.add(gerichtBestellungs.get(i).getGericht().getName() + gerichtBestellungs.get(i).getBestellung().getId());
            anzahlDerBestellungen++;
            timestamps.add(gerichtBestellungs.get(i).getBestellung().getBestelldatum());
        }

        List<Timestamp> timestampsWithoutDuplicates = timestamps.stream()
                .distinct()
                .collect(Collectors.toList());

        Map<String, Integer> duplicateCountMap = names
                .stream()
                .collect(
                        Collectors.toMap(Function.identity(), name -> 1, Math::addExact)
                );

        duplicateCountMap.forEach(
                (key, value) -> System.out.println("Key : " + key + "\t Count : " + value)
        );

//        HashMap<String, HashMap<Integer, GerichtDTO>> bestellungNeu = new HashMap<>();
//
//        for (GerichtBestellung bestellung: gerichtBestellungs) {
//            bestellungNeu.put(bestellung.getBestellung().getGerichteBestellungen(), bestellung.getBestellung().getId());
//            for (GerichtBestellung gericht : gerichtBestellungs){
//
//            }
//        }
        List<Bestellung> alleBestellungen = bestellungService.alleBestellungen(foundMandant.getId());


        List<BestellungModel> modeldata = new ArrayList<>();

        for (Bestellung bestellung:alleBestellungen) {
          //  gerichtBestellungList.add(bestellung.getGerichteBestellungen());

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
            }

            modeldata.add(new BestellungModel(gerichtBestellungModelList,"jett","Zusatzinfo"));
        }

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
