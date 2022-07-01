package com.htlimst.lieferrex.controller.oeffnungszeiten;

import com.htlimst.lieferrex.model.enums.WochentagEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
