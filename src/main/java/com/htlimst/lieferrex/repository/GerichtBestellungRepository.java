package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.GerichtBestellung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GerichtBestellungRepository extends JpaRepository<GerichtBestellung, Long> {
    @Query(value = "SELECT COUNT(b.bestellung_id) FROM gericht_bestellung as gb INNER JOIN bestellung as b ON gb.bestellung_id = b.bestellung_id WHERE b.mandant_id = ?1", nativeQuery = true)
    long getVerkaufteGerichteByMandant(long mandantId);
}
