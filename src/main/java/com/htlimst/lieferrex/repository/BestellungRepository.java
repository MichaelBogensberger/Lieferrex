package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Bestellung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestellungRepository extends JpaRepository<Bestellung,Long> {
}
