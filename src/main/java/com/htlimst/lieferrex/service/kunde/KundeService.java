package com.htlimst.lieferrex.service.kunde;

import com.htlimst.lieferrex.dto.KundeRegistrationDto;
import com.htlimst.lieferrex.model.Kunde;

public interface KundeService {

    Kunde save(KundeRegistrationDto userRegistrationDto);


}
