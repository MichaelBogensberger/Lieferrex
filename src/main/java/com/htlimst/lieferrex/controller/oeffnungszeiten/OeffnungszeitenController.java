package com.htlimst.lieferrex.controller.oeffnungszeiten;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Oeffnungszeit;
import com.htlimst.lieferrex.model.enums.WochentagEnum;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.oeffnungszeiten.OeffnungszeitenService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/dashboard/oeffnungszeiten")
public class OeffnungszeitenController {
    private OeffnungszeitenService oeffnungszeitenService;
    private AngestellterService angestellterService;

    @Autowired
    public OeffnungszeitenController(OeffnungszeitenService oeffnungszeitenService, AngestellterService angestellterService) {
        this.oeffnungszeitenService = oeffnungszeitenService;
        this.angestellterService = angestellterService;
    }

    @GetMapping
    public String showWebpage(@AuthenticationPrincipal UserPrincipal principal, Model model) throws ParseException {
        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();
        List<Oeffnungszeit> oeffnungszeitList = oeffnungszeitenService.alleOeffnungszeiten(foundMandant);
        List<OeffnungsDarstellungModel> oeffnungensZeiten = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        String endepause = null;
        String oeffnungszit = null;
        String schliessungszeit = null;
        String startpause = null;
        String elementId = null;
        String elementId2 = null;
        String idFieldOffenVon = null;
        String idFieldOffenBis = null;
        String idFieldPausenVon = null;
        String idFieldPausenBis = null;

        int counter = 1;

        for (Oeffnungszeit oeffnungszeit : oeffnungszeitList){
            String fieldname = "fn";

            if(oeffnungszeit.getEndepause() != null){
                endepause = oeffnungszeit.getEndepause().toLocalTime().format(dtf);
            }
            if(oeffnungszeit.getOeffnungszeit() != null){
                oeffnungszit = oeffnungszeit.getOeffnungszeit().toLocalTime().format(dtf);
            }
            if(oeffnungszeit.getSchliessungszeit() != null){
                schliessungszeit = oeffnungszeit.getSchliessungszeit().toLocalTime().format(dtf);
            }
            if(oeffnungszeit.getStartpause() != null){
                startpause = oeffnungszeit.getStartpause().toLocalTime().format(dtf);
            }
            if(oeffnungszeit.getTag() == WochentagEnum.MONDAY){
                elementId = "pause-act-mo";
                elementId2 = "day-act-mo";
                idFieldOffenVon = "day-von-mo";
                idFieldOffenBis = "day-bis-mo";
                idFieldPausenVon = "pause-von-mo";
                idFieldPausenBis = "pause-bis-mo";
            }else if(oeffnungszeit.getTag() == WochentagEnum.TUESDAY){
                elementId = "pause-act-di";
                elementId2 = "day-act-di";
                idFieldOffenVon = "day-von-di";
                idFieldOffenBis = "day-bis-di";
                idFieldPausenVon = "pause-von-di";
                idFieldPausenBis = "pause-bis-di";
            } else if(oeffnungszeit.getTag() == WochentagEnum.WEDNESDAY){
                elementId = "pause-act-mi";
                elementId2 = "day-act-mi";
                idFieldOffenVon = "day-von-mi";
                idFieldOffenBis = "day-bis-mi";
                idFieldPausenVon = "pause-von-mi";
                idFieldPausenBis = "pause-bis-mi";
            } else if(oeffnungszeit.getTag() == WochentagEnum.THURSDAY){
                elementId = "pause-act-do";
                elementId2 = "day-act-do";
                idFieldOffenVon = "day-von-do";
                idFieldOffenBis = "day-bis-do";
                idFieldPausenVon = "pause-von-do";
                idFieldPausenBis = "pause-bis-do";
            } else if(oeffnungszeit.getTag() == WochentagEnum.FRIDAY){
                elementId = "pause-act-fr";
                elementId2 = "day-act-fr";
                idFieldOffenVon = "day-von-fr";
                idFieldOffenBis = "day-bis-fr";
                idFieldPausenVon = "pause-von-fr";
                idFieldPausenBis = "pause-bis-fr";
            } else if(oeffnungszeit.getTag() == WochentagEnum.SATURDAY){
                elementId = "pause-act-sa";
                elementId2 = "day-act-sa";
                idFieldOffenVon = "day-von-sa";
                idFieldOffenBis = "day-bis-sa";
                idFieldPausenVon = "pause-von-sa";
                idFieldPausenBis = "pause-bis-sa";
            } else if(oeffnungszeit.getTag() == WochentagEnum.SUNDAY){
                elementId = "pause-act-so";
                elementId2 = "day-act-so";
                idFieldOffenVon = "day-von-so";
                idFieldOffenBis = "day-bis-so";
                idFieldPausenVon = "pause-von-so";
                idFieldPausenBis = "pause-bis-so";
            }

            oeffnungensZeiten.add(new OeffnungsDarstellungModel(endepause, oeffnungszit, schliessungszeit, startpause, oeffnungszeit.getTag(), elementId , elementId2, idFieldOffenVon, idFieldOffenBis
            , idFieldPausenVon, idFieldPausenBis));
        }

        Oeffnungszeit neueOeffnungszeit = new Oeffnungszeit();
        neueOeffnungszeit.setMandant(foundMandant);

        model.addAttribute("oeffnungsz", neueOeffnungszeit);
        model.addAttribute("oeffnungszeiten", oeffnungensZeiten);
        return "dashboard/oeffnungszeiten.html";
    }

    @PostMapping("/save")
    public String saveOeffnungszeiten(Oeffnungszeit oeffnungszeit,
                                      @RequestParam String fn1,
                                      @RequestParam String fn2,
                                      @RequestParam String fn3,
                                      @RequestParam String fn4,
                                      @RequestParam Optional<Boolean> btn1,
                                      @RequestParam Optional<Boolean> btn2

    ){
        System.out.println(fn1);
        System.out.println(fn2);
        System.out.println(fn3);
        System.out.println(fn4);
        System.out.println(btn1);
        System.out.println(btn2);

        if(btn2.isPresent() && btn1.isPresent()){
            System.out.println("btn1 + btn2");
        } else if (btn1.isPresent()){
            System.out.println("only btn1");
        }

        return "redirect:/dashboard/oeffnungszeiten";
    }

}
