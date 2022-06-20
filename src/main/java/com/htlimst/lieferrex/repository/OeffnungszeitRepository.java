package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.exceptions.OeffnungszeitNotFoundException;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Oeffnungszeit;
import com.htlimst.lieferrex.model.Wochentag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OeffnungszeitRepository extends JpaRepository<Oeffnungszeit,Long> {
    Oeffnungszeit findOeffnungszeitsByMandantAndTag(Mandant mandant, Wochentag wochentag) throws OeffnungszeitNotFoundException;
}
