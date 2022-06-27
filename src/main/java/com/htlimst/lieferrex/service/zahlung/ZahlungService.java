package com.htlimst.lieferrex.service.zahlung;

import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Umsatz;

import java.util.List;
import java.util.Optional;

public interface ZahlungService {
    List<Umsatz> getUmsatzByMandant(Mandant mandant);
    List<Bestellung> letztenDreiBestellungne(long mandantId);
    List<Bestellung> alleBestellungen(long mandantId);
}