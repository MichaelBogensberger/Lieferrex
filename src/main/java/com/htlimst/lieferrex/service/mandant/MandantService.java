package com.htlimst.lieferrex.service.mandant;

import com.htlimst.lieferrex.dto.KundeRegistrationDto;
import com.htlimst.lieferrex.dto.MandantRegistrationDto;
import com.htlimst.lieferrex.dto.MandantSuchDto;
import com.htlimst.lieferrex.exceptions.MandantNotFoundException;
import com.htlimst.lieferrex.model.Kunde;
import com.htlimst.lieferrex.model.Mandant;

import java.util.List;
import java.util.Optional;

public interface MandantService {
    List<Mandant> alleMandanten();
    Mandant mandantEinfuegen(Mandant mandant);
    Mandant mandantMitId(Long id); //throws MandantNichtGefunden
    void mandantLoeschenMitId(Long id);
    Optional<Mandant> findMandantByFirmenname(String name);
    Optional<Mandant> findMandantByAngestellterEmail(String email);
    Optional<Mandant> findMandantById(Long id);
    Mandant listAll();
    void save(Mandant mandant);
    public List<MandantSuchDto> findMandantByPlz(String Adresse, boolean isGeöffnet, double lieferKosten, double mindestbestellwert, String kategorie) throws MandantNotFoundException;

    boolean saveRegistrationDto(MandantRegistrationDto mandantRegistrationDto);


}
