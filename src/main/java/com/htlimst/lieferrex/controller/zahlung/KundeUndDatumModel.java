package com.htlimst.lieferrex.controller.zahlung;

import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.Kunde;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class KundeUndDatumModel {
    private Bestellung bestellung;
    private String datum;

    public KundeUndDatumModel(Bestellung bestellung, String datum) {
        this.bestellung = bestellung;
        this.datum = datum;
    }
}
