package com.htlimst.lieferrex.service.bestellung;

import com.htlimst.lieferrex.dto.BezahlDto;
import com.htlimst.lieferrex.dto.EinkaufswagenDto;
import com.htlimst.lieferrex.model.Bestellstatus;
import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.enums.BestellstatusEnum;
import com.htlimst.lieferrex.service.security.UserPrincipal;

import java.util.List;

public interface BestellungService {
    public List<Bestellung> alleBestellungen(Long mandantId, Bestellstatus bestellstatus);
    public void bestellungAufgeben(EinkaufswagenDto einkaufswegenDtoString, UserPrincipal userPrincipal, BezahlDto bezahlDto);
    public EinkaufswagenDto deserializeEinkaufswagen(String einkaufswagen);
    public Bestellung bestellungByIdAnzeigen(long bestellid);
    public Bestellstatus bestellstatusAnzeigen(BestellstatusEnum bestellstatusEnum);
    public void save(Bestellung bestellung);
    BezahlDto getBezahlDto(EinkaufswagenDto einkaufswagenDto);
}
