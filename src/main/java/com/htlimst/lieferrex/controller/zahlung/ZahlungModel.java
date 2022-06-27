package com.htlimst.lieferrex.controller.zahlung;

import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.Umsatz;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ZahlungModel {
    private int zahlungen;
    private double proBestellung;
    private double diesenMonat;
    private double umsatzImMonat;
    private int anzahlAnOrdersImMonat;
    private double durschnittProBestellungImMonat;
    private double umsatzImJahr;
    private int anzahlAnOrdersImJahr;
    private double durchschnittProBestellungImJahr;
    private List<Bestellung> letzteZahlungen;
    private List<KundeUndDatumModel> kunde;

    public ZahlungModel(int zahlungen, double proBestellung, double diesenMonat, double umsatzImMonat, int anzahlAnOrdersImMonat, double durschnittProBestellungImMonat, double umsatzImJahr, int anzahlAnOrdersImJahr, double durchschnittProBestellungImJahr, List<Bestellung> letzteZahlungen, List<KundeUndDatumModel> kunde) {
        this.zahlungen = zahlungen;
        this.proBestellung = proBestellung;
        this.diesenMonat = diesenMonat;
        this.umsatzImMonat = umsatzImMonat;
        this.anzahlAnOrdersImMonat = anzahlAnOrdersImMonat;
        this.durschnittProBestellungImMonat = durschnittProBestellungImMonat;
        this.umsatzImJahr = umsatzImJahr;
        this.anzahlAnOrdersImJahr = anzahlAnOrdersImJahr;
        this.durchschnittProBestellungImJahr = durchschnittProBestellungImJahr;
        this.letzteZahlungen = letzteZahlungen;
        this.kunde = kunde;
    }
}
