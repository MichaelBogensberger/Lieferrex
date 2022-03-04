package com.htlimst.lieferrex.service.mandant;

import com.htlimst.lieferrex.model.Mandant;

import java.util.List;
import java.util.Optional;

public interface MandantService {
    List<Mandant> alleMandanten();
    Mandant mandantEinfuegen(Mandant mandant);
    Mandant mandantMitId(Long id); //throws MandantNichtGefunden
    void mandantLoeschenMitId(Long id);

    Optional<Mandant> findMandantByFirmenname(String name);

    Mandant listAll();


}
