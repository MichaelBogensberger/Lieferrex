package com.htlimst.lieferrex.controller.oeffnungszeiten;

import com.htlimst.lieferrex.model.enums.WochentagEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalTime;

@Getter
@Setter
public class OeffnungsDarstellungModel {
    private String endepause;
    private String oeffnungszeit;
    private String schliessungszeit;
    private String startpause;
    private WochentagEnum tag;
    private String eid;
    private String eid2;
    private String idFieldOffenVon;
    private String idFieldOffenBis;
    private String idFieldPausenVon;
    private String idFieldPausenBis;

    public OeffnungsDarstellungModel(String endepause, String oeffnungszeit, String schliessungszeit, String startpause, WochentagEnum tag, String eid, String eid2, String idFieldOffenVon, String idFieldOffenBis, String idFieldPausenVon, String idFieldPausenBis) {
        this.endepause = endepause;
        this.oeffnungszeit = oeffnungszeit;
        this.schliessungszeit = schliessungszeit;
        this.startpause = startpause;
        this.tag = tag;
        this.eid = eid;
        this.eid2 = eid2;
        this.idFieldOffenVon = idFieldOffenVon;
        this.idFieldOffenBis = idFieldOffenBis;
        this.idFieldPausenVon = idFieldPausenVon;
        this.idFieldPausenBis = idFieldPausenBis;
    }
}
