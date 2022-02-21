package com.htlimst.lieferrex.service.gericht;

import com.htlimst.lieferrex.model.Gericht;

import java.util.List;

public interface GerichtService {
    public Gericht gerichtEinfuegen(Gericht gericht);
    public Gericht gerichtAusgebenById(Long id);
    public Gericht gerichtUpdate(Long id);
    public void gerichtLoeschen(Long id);
    public List<Gericht> gerichtAusgabe();
}