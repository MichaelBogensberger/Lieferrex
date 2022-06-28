package com.htlimst.lieferrex.service.overview;

import com.htlimst.lieferrex.dto.MandantRegistrationDto;
import com.htlimst.lieferrex.model.*;

import java.util.List;
import java.util.Optional;

public interface OverviewService {
    public Seitenaufrufe seitenaufrufe(Mandant mandant);
    public Umsatz umsatz(Mandant mandant);
    public long gericht();
    public long getVerkaufteGerichte(long mandantId);
    public List<Bestellung> getLatestBestellungen(long mandantId);
    public List<Gericht> anzahlGeakuft(long mandantId);
    public List<Umsatz> alleUmsaetzeByMandant(Mandant mandant);
    public boolean checkIfUmsatzImMonatVorhanden(Mandant mandant);
}
