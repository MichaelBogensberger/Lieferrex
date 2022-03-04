package com.htlimst.lieferrex.service.angestellter;

import com.htlimst.lieferrex.model.Angestellter;

public interface AngestellterService {

    Angestellter findByEmail(String email);

}
