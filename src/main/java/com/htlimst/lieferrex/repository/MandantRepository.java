package com.htlimst.lieferrex.repository;

import java.util.Optional;

import com.htlimst.lieferrex.model.Mandant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MandantRepository extends JpaRepository<Mandant,Long> {
    Optional<Mandant> findMandantByFirmenname(String name);
}
