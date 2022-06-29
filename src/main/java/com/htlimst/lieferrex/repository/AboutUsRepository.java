package com.htlimst.lieferrex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.htlimst.lieferrex.model.AboutUs;
import com.htlimst.lieferrex.model.Mandant;

@Repository
public interface AboutUsRepository extends JpaRepository<AboutUs, Long> {
    AboutUs save(AboutUs aboutUs);

    void delete(AboutUs aboutUs);

    Optional<AboutUs> findAboutusByMandant(Mandant mandant);
}
