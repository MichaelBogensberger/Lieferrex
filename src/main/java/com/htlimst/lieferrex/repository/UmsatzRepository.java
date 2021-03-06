package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.constraint.fieldMatch.FieldMatch;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Umsatz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UmsatzRepository extends JpaRepository<Umsatz,Long> {
    Umsatz getUmsatzByMandant(Mandant mandant);
    List<Umsatz> getAllByMandant(Mandant mandant);
    Umsatz findFirstByMandantOrderByJahrDescMonatDesc(Mandant mandant);
}
