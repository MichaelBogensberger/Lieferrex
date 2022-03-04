package com.htlimst.lieferrex.service.gericht;

import com.htlimst.lieferrex.model.Gericht;

import java.util.List;
import java.util.Optional;

public interface GerichtService {
    public List<Gericht> getGerichte();
    public Optional<Gericht> getGerichtById(Long id);
    public List<Gericht> getGerichtByStatus();
    public List<Gericht> getGerichtByStatusZero();

    Gericht apiUpdateGericht(Long id, Gericht givenGericht);

}