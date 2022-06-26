package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Bestellstatus;
import com.htlimst.lieferrex.model.Bestellung;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BestellungRepository extends JpaRepository<Bestellung,Long> {

    @Query(value = "SELECT * FROM bestellung as b INNER JOIN kunde as k ON b.kunde_id = k.kunde_id INNER JOIN bestellart as ba ON b.bestellart_id = ba.bestellart_id WHERE b.mandant_id = ?1 ORDER BY bestelldatum DESC LIMIT 3  ", nativeQuery = true)
    List<Bestellung> getLatestThreeEntries(long mandantId);
    List<Bestellung> getBestellungByMandant_IdAndBestellstatus(long mandantId, Bestellstatus bestellstatus);
    Bestellung getBestellungById(long bestellId);

    Optional<Bestellung> findTopByOrderByIdDesc();
    List<Bestellung> getBestellungByMandant_Id(long mandantId);
}
