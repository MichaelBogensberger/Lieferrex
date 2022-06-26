package com.htlimst.lieferrex.service.zustellung;

import com.htlimst.lieferrex.model.Bestellstatus;
import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.enums.BestellstatusEnum;

import java.util.List;

public interface ZustellungService {
    public List<Bestellung> alleBestellungen(Long mandantId, Bestellstatus bestellstatus);
    public Bestellung bestellungByIdAnzeigen(long bestellid);
    public Bestellstatus bestellstatusAnzeigen(BestellstatusEnum bestellstatusEnum);
    public List<Bestellung> bestellungenByMandantId(long mandantId);
    public void save(Bestellung bestellung);
}
