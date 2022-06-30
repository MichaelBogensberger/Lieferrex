package com.htlimst.lieferrex.service.kunde;

import com.htlimst.lieferrex.dto.KundeRegistrationDto;
import com.htlimst.lieferrex.dto.PasswortAendernDto;
import com.htlimst.lieferrex.model.Kunde;
import com.htlimst.lieferrex.service.security.UserPrincipal;

public interface KundeService {

    Kunde save(KundeRegistrationDto userRegistrationDto);
    boolean passwortAendern(PasswortAendernDto passwortAendernDto, UserPrincipal principal);

}
