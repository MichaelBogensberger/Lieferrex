package com.htlimst.lieferrex.service.bestellung;

import com.htlimst.lieferrex.dto.EinkaufswagenDatailDto;
import com.htlimst.lieferrex.dto.EinkaufswagenDto;
import com.htlimst.lieferrex.model.Bestellart;
import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.Kunde;
import com.htlimst.lieferrex.repository.BestellungRepository;
import com.htlimst.lieferrex.repository.KundeRepository;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BestellungServiceImpl implements BestellungService{

    private BestellungRepository bestellungRepository;
    private KundeRepository kundeRepository;
    private MandantRepository mandantRepository;

    @Autowired
    public BestellungServiceImpl(BestellungRepository bestellungRepository, KundeRepository kundeRepository, MandantRepository mandantRepository) {
        this.bestellungRepository = bestellungRepository;
        this.kundeRepository = kundeRepository;
        this.mandantRepository = mandantRepository;
    }

    @Override
    public List<Bestellung> alleBestellungen(Long mandantId) {
        return bestellungRepository.getBestellungByMandant_Id(mandantId);
    }

    @Override
    public void makeBestellung(EinkaufswagenDto einkaufswagenDto, UserPrincipal userPrincipal) {


//        Bestellung bestellung = new Bestellung().builder().id(null)
//                .dauer(15).bestelldatum(Timestamp.valueOf(LocalDateTime.now()))
//                .gesamtpreis(0.0).trinkgeld(0.0)
//                .bestellart(null)
//                .kunde(kundeRepository.findByEmail(userPrincipal.getUsername()))
//                .mandant(mandantRepository.getById(einkaufswagenDto.))
//                .bestellstatus().build();
//
//        bestellungRepository.save(bestellung);
    }

    @Override
    public EinkaufswagenDto deserializeEinkaufswagen(String einkaufswagen) {

        EinkaufswagenDatailDto einkaufswagenDatailDto = new EinkaufswagenDatailDto().builder().gerichtID(1L).anmerkung("Extra Tomaten").anzahl(1).build();

        List<EinkaufswagenDatailDto> einkaufswagenDatailDtoList = new ArrayList<>();
        einkaufswagenDatailDtoList.add(einkaufswagenDatailDto);

        EinkaufswagenDto einkaufswagenDto = new EinkaufswagenDto().builder().mandantId(1L).einkaufswagenDatails(einkaufswagenDatailDtoList).build();

        return einkaufswagenDto;
    }


}
