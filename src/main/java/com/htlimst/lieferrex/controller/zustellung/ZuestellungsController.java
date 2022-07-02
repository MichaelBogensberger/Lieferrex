package com.htlimst.lieferrex.controller.zustellung;

import com.htlimst.lieferrex.controller.zubereitung.BestellungModel;
import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.model.enums.BestellartEnum;
import com.htlimst.lieferrex.model.enums.BestellstatusEnum;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import com.htlimst.lieferrex.service.zustellung.ZustellungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/dashboard/zustellungen")
public class ZuestellungsController {
    public AngestellterService angestellterService;
    public ZustellungService zustellungService;

    @Autowired
    public ZuestellungsController(AngestellterService angestellterService, ZustellungService zustellungService) {
        this.angestellterService = angestellterService;
        this.zustellungService = zustellungService;
    }

    @GetMapping
    public String showDashboardZustellungen(@AuthenticationPrincipal UserPrincipal principal, Model model) {
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();
        List<Bestellung> alleBestellungen = zustellungService.bestellungenByMandantId(foundMandant.getId());

        List<ZubereitungsModel> modeldata = new ArrayList<>();
        List<ZubereitungsModel> modeldataAbholung = new ArrayList<>();
        int abholung = 0;
        int lieferung = 0;

        for (Bestellung bestellung:alleBestellungen) {
            if(bestellung.getBestellstatus().getBestellstatus() == BestellstatusEnum.FERTIG_ZUM_ABHOLEN || bestellung.getBestellstatus().getBestellstatus() == BestellstatusEnum.IN_AUSLIEFERUNG){
                if(bestellung.getBestellart().getBestellart() == BestellartEnum.LIEFERUNG){
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

                    lieferung += 1;
                    modeldata.add(new ZubereitungsModel(gerichtBestellungModelList, lieferung, bestellung.getId(), bestellung.getKunde()));
                }

                if(bestellung.getBestellart().getBestellart() == BestellartEnum.ABHOLUNG){
                    HashMap<String, Integer> gerichtBestellungModelListAbholung = new HashMap<>();
                    for(GerichtBestellung gerichtBestellung : bestellung.getGerichteBestellungen())
                    {

                        if(!gerichtBestellungModelListAbholung.containsKey(gerichtBestellung.getGericht().getName()))
                        {
                            gerichtBestellungModelListAbholung.put(gerichtBestellung.getGericht().getName(),1);
                        } else
                        {
                            gerichtBestellungModelListAbholung.put(gerichtBestellung.getGericht().getName(),gerichtBestellungModelListAbholung.get(gerichtBestellung.getGericht().getName())+1);
                        }
                    }

                    abholung += 1;
                    modeldataAbholung.add(new ZubereitungsModel(gerichtBestellungModelListAbholung, abholung, bestellung.getId(), bestellung.getKunde()));
                }
            }
        }
        model.addAttribute("zustellungLieferungDetail", modeldata);
        model.addAttribute("zustellungAbholungDetail",modeldataAbholung);
        model.addAttribute("user", foundAngestellter.getVorname() + ' ' + foundAngestellter.getNachname());
        model.addAttribute("vname", foundAngestellter.getVorname());
        model.addAttribute("nname", foundAngestellter.getNachname());
        model.addAttribute("firmenname", foundMandant.getFirmenname());

        return "dashboard/zustellung.html";
    }

    @PostMapping
    public String checkingLieferungAndAbholung(@AuthenticationPrincipal UserPrincipal principal, Model model, @RequestParam Optional<Long> abholungChangeToFertigZumAbholen, @RequestParam Optional<Long> lieferungChangeToFertigZumAbholen){
        showDashboardZustellungen(principal, model);
        if(abholungChangeToFertigZumAbholen.isPresent()){
            Bestellung bestellung =  zustellungService.bestellungByIdAnzeigen(abholungChangeToFertigZumAbholen.get());
            bestellung.setBestellstatus(zustellungService.bestellstatusAnzeigen(BestellstatusEnum.ABGESCHLOSSEN));
            zustellungService.save(bestellung);
        } else if (lieferungChangeToFertigZumAbholen.isPresent()){
            Bestellung bestellung = zustellungService.bestellungByIdAnzeigen(lieferungChangeToFertigZumAbholen.get());
            bestellung.setBestellstatus(zustellungService.bestellstatusAnzeigen(BestellstatusEnum.ABGESCHLOSSEN));
            zustellungService.save(bestellung);
        }
        return "redirect:/dashboard/zustellungen";
    }

}
