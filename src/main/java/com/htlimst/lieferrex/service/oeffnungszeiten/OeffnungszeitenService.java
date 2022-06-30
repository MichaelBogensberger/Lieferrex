package com.htlimst.lieferrex.service.oeffnungszeiten;

import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Oeffnungszeit;

import java.util.List;

public interface OeffnungszeitenService {
    public List<Oeffnungszeit> alleOeffnungszeiten(Mandant mandant);

}
