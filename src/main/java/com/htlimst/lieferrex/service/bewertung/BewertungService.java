package com.htlimst.lieferrex.service.bewertung;

import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.Mandant;

import java.util.List;

public interface BewertungService {
    List<Bestellung> alleBewertungenByMandant(Mandant mandant);

}
