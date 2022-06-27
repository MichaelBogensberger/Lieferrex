package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Oeffnungszeit;
import com.htlimst.lieferrex.model.enums.WochentagEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OeffnungszeitRepository extends JpaRepository<Oeffnungszeit,Long> {
    Optional<Oeffnungszeit> findOeffnungszeitsByMandantAndTag(Mandant mandant, WochentagEnum wochentag);
    List<Oeffnungszeit> findOeffnungszeitByMandant(Mandant mandant);
}
