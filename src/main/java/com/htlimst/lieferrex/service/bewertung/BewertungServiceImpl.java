package com.htlimst.lieferrex.service.bewertung;

import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.repository.BestellungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BewertungServiceImpl implements BewertungService{

    private BestellungRepository bestellungRepository;

    @Autowired
    public BewertungServiceImpl(BestellungRepository bestellungRepository) {
        this.bestellungRepository = bestellungRepository;
    }


    @Override
    public List<Bestellung> alleBewertungenByMandant(Mandant mandant) {
        return bestellungRepository.getBestellungByMandant_Id(mandant.getId());
    }
}
