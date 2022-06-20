package com.htlimst.lieferrex.service.bestellung;

import com.htlimst.lieferrex.model.Bestellung;

import java.util.List;

public interface BestellungService {
    public List<Bestellung> alleBestellungen(Long mandantId);
}
