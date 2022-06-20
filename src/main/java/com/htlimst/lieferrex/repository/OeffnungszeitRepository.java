package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Oeffnungszeit;
import com.htlimst.lieferrex.model.Wochentag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OeffnungszeitRepository extends JpaRepository<Oeffnungszeit,Long> {
    Optional<Oeffnungszeit> findOeffnungszeitsByMandantAndTag(Mandant mandant, Wochentag wochentag);
}
