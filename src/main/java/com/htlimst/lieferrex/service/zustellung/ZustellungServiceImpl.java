package com.htlimst.lieferrex.service.zustellung;

import com.htlimst.lieferrex.dto.EinkaufswagenDatailDto;
import com.htlimst.lieferrex.dto.EinkaufswagenDto;
import com.htlimst.lieferrex.model.Bestellstatus;
import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.enums.BestellstatusEnum;
import com.htlimst.lieferrex.repository.BestellstatusRepository;
import com.htlimst.lieferrex.repository.BestellungRepository;
import com.htlimst.lieferrex.repository.KundeRepository;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZustellungServiceImpl implements ZustellungService {

    private BestellungRepository bestellungRepository;
    private BestellstatusRepository bestellstatusRepository;

    @Autowired
    public ZustellungServiceImpl(BestellungRepository bestellungRepository, BestellstatusRepository bestellstatusRepository) {
        this.bestellungRepository = bestellungRepository;
        this.bestellstatusRepository = bestellstatusRepository;
    }

    @Override
    public List<Bestellung> alleBestellungen(Long mandantId, Bestellstatus bestellstatus) {
        return bestellungRepository.getBestellungByMandant_IdAndBestellstatus(mandantId,bestellstatus);
    }

    @Override
    public Bestellung bestellungByIdAnzeigen(long bestellid) {
        return bestellungRepository.getBestellungById(bestellid);
    }

    @Override
    public Bestellstatus bestellstatusAnzeigen(BestellstatusEnum bestellstatusEnum) {
        return bestellstatusRepository.getBestellstatusByBestellstatus(bestellstatusEnum);
    }

    @Override
    public List<Bestellung> bestellungenByMandantId(long mandantId) {
        return bestellungRepository.getBestellungByMandant_Id(mandantId);
    }

    @Override
    public void save(Bestellung bestellung) {
        bestellungRepository.save(bestellung);
    }

}
