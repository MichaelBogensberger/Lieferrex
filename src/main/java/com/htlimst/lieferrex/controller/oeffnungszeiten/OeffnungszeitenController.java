package com.htlimst.lieferrex.controller.oeffnungszeiten;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Oeffnungszeit;
import com.htlimst.lieferrex.model.enums.WochentagEnum;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.oeffnungszeiten.OeffnungszeitenService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import net.bytebuddy.build.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
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

        OeffnungListModel oeffnungListModel = new OeffnungListModel();

        for (Oeffnungszeit oeffnungszeit : oeffnungszeitList){
            endepause = null;
            oeffnungszit = null;
            schliessungszeit = null;
            startpause = null;

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

        oeffnungListModel.setList(oeffnungensZeiten);

        model.addAttribute("oeffnungsz", neueOeffnungszeit);
        model.addAttribute("oeffnungszeiten", oeffnungensZeiten);
        model.addAttribute("ozl", oeffnungListModel);
        model.addAttribute("user", foundAngestellter.getVorname() + ' ' + foundAngestellter.getNachname());
        model.addAttribute("vname", foundAngestellter.getVorname());
        model.addAttribute("nname", foundAngestellter.getNachname());
        model.addAttribute("firmenname", foundMandant.getFirmenname());
        return "dashboard/oeffnungszeiten.html";
    }

    @PostMapping("/save")
    public String saveOeffnungszeiten(OeffnungListModel oeffnungListModel, @AuthenticationPrincipal UserPrincipal principal) throws ParseException {

        Angestellter foundAngestellter = angestellterService.findByEmail(principal.getUsername());
        Mandant foundMandant = foundAngestellter.getMandant();
        List<Oeffnungszeit> oeffnungszeitList = oeffnungszeitenService.alleOeffnungszeiten(foundMandant);

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        int counter = 0;

        if (oeffnungListModel.getList() != null){
            for (OeffnungsDarstellungModel oeffnungsDarstellungModel : oeffnungListModel.getList()) {

                if (oeffnungsDarstellungModel.getOeffnungszeit() == null || oeffnungsDarstellungModel.getOeffnungszeit().length() == 0) {
                    oeffnungszeitList.get(counter).setOeffnungszeit(null);
                } else {
                    oeffnungszeitList.get(counter).setOeffnungszeit(new Time(formatter.parse(oeffnungsDarstellungModel.getOeffnungszeit()).getTime()));
                }
                if (oeffnungsDarstellungModel.getSchliessungszeit() == null || oeffnungsDarstellungModel.getSchliessungszeit().length() == 0){
                    oeffnungszeitList.get(counter).setSchliessungszeit(null);
                } else {
                    oeffnungszeitList.get(counter).setSchliessungszeit(new Time(formatter.parse(oeffnungsDarstellungModel.getSchliessungszeit()).getTime()));
                }
                if (oeffnungsDarstellungModel.getStartpause() == null || oeffnungsDarstellungModel.getStartpause().length() == 0){
                    oeffnungszeitList.get(counter).setStartpause(null);
                } else {
                    oeffnungszeitList.get(counter).setStartpause(new Time(formatter.parse(oeffnungsDarstellungModel.getStartpause()).getTime()));
                }
                if (oeffnungsDarstellungModel.getEndepause() == null || oeffnungsDarstellungModel.getEndepause().length() == 0){
                    oeffnungszeitList.get(counter).setEndepause(null);
                } else {
                    oeffnungszeitList.get(counter).setEndepause(new Time(formatter.parse(oeffnungsDarstellungModel.getEndepause()).getTime()));
                }

                counter++;
            }
        }

        oeffnungszeitenService.saveOeffnungszeiten(oeffnungszeitList);

        return "redirect:.";
    }

}
