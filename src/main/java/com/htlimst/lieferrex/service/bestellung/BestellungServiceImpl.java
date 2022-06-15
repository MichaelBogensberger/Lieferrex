package com.htlimst.lieferrex.service.bestellung;

import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.repository.BestellungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BestellungServiceImpl implements BestellungService{

    private BestellungRepository bestellungRepository;

    @Autowired
    public BestellungServiceImpl(BestellungRepository bestellungRepository) {
        this.bestellungRepository = bestellungRepository;
    }

    @Override
    public List<Bestellung> alleBestellungen(Long mandantId) {
        return bestellungRepository.getBestellungByMandant_Id(mandantId);
    }
}
