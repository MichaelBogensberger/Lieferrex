package com.htlimst.lieferrex.service.zahlung;

import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Umsatz;
import com.htlimst.lieferrex.repository.BestellstatusRepository;
import com.htlimst.lieferrex.repository.BestellungRepository;
import com.htlimst.lieferrex.repository.GerichtRepository;
import com.htlimst.lieferrex.repository.UmsatzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZahlungServiceImpl implements ZahlungService {

    private UmsatzRepository umsatzRepository;
    private BestellungRepository bestellungRepository;

    @Autowired
    public ZahlungServiceImpl(UmsatzRepository umsatzRepository, BestellungRepository bestellungRepository) {
        this.umsatzRepository = umsatzRepository;
        this.bestellungRepository = bestellungRepository;
    }

    @Override
    public List<Umsatz> getUmsatzByMandant(Mandant mandant) {
        return umsatzRepository.getAllByMandant(mandant);
    }

    @Override
    public List<Bestellung> letztenDreiBestellungne(long mandantId) {
        return bestellungRepository.getLatestThreeEntries(mandantId);
    }

    @Override
    public List<Bestellung> alleBestellungen(long mandantId) {
        return bestellungRepository.getBestellungByMandant_Id(mandantId);
    }
}