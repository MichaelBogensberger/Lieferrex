package com.htlimst.lieferrex.repository;

import java.util.List;
import java.util.Optional;

import com.htlimst.lieferrex.model.Mandant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MandantRepository extends JpaRepository<Mandant,Long> {
    Optional<Mandant> findMandantByFirmenname(String name);
    Optional<Mandant> findMandantByAngestellte_Email(String email);

    List<Mandant> findMandantByOrt(String ort);
    List<Mandant> findMandantByPlz(String plz);

}
