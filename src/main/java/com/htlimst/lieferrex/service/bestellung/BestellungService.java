package com.htlimst.lieferrex.service.bestellung;

import com.htlimst.lieferrex.dto.BestellDto;
import com.htlimst.lieferrex.dto.BezahlDto;
import com.htlimst.lieferrex.dto.EinkaufswagenDto;
import com.htlimst.lieferrex.model.Bestellstatus;
import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.enums.BestellstatusEnum;
import com.htlimst.lieferrex.service.security.UserPrincipal;

import java.util.List;

public interface BestellungService {
    List<Bestellung> alleBestellungen(Long mandantId, Bestellstatus bestellstatus);
    void bestellungAufgeben(EinkaufswagenDto einkaufswegenDtoString, UserPrincipal userPrincipal, BezahlDto bezahlDto);
    EinkaufswagenDto deserializeEinkaufswagen(String einkaufswagen);
    Bestellung bestellungByIdAnzeigen(long bestellid);
    Bestellstatus bestellstatusAnzeigen(BestellstatusEnum bestellstatusEnum);
    void save(Bestellung bestellung);
    BezahlDto getBezahlDto(EinkaufswagenDto einkaufswagenDto);
    BestellDto getBestellDto(String KundenEmail);

}
