package com.htlimst.lieferrex.controller.Bewertung;


import com.htlimst.lieferrex.model.Bestellung;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
public class BewertungUndDatumModel {
    private Bestellung bestellung;
    private String datum;
    private String zeit;

    public BewertungUndDatumModel(Bestellung bestellung, String datum, String zeit) {
        this.bestellung = bestellung;
        this.datum = datum;
        this.zeit = zeit;
    }
}
