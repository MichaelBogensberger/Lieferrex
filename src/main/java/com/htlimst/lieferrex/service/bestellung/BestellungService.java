package com.htlimst.lieferrex.service.bestellung;

import com.htlimst.lieferrex.dto.EinkaufswagenDto;
import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.service.security.UserPrincipal;

import java.util.List;

public interface BestellungService {
    public List<Bestellung> alleBestellungen(Long mandantId);
    public void makeBestellung(EinkaufswagenDto einkaufswegenDtoString, UserPrincipal userPrincipal);
    public EinkaufswagenDto deserializeEinkaufswagen(String einkaufswagen);
}
