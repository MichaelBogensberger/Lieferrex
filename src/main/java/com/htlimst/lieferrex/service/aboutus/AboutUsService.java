package com.htlimst.lieferrex.service.aboutus;

import java.util.Optional;

import com.htlimst.lieferrex.model.AboutUs;
import com.htlimst.lieferrex.model.Mandant;

public interface AboutUsService {

    AboutUs save(AboutUs aboutUs);

    void delete(AboutUs aboutUs);

    Optional<AboutUs> findAboutusByMandant(Mandant mandant);

}
