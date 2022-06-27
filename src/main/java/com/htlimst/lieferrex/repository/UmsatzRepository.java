package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.constraint.fieldMatch.FieldMatch;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Umsatz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UmsatzRepository extends JpaRepository<Umsatz,Long> {
    public Umsatz getUmsatzByMandant(Mandant mandant);
    public List<Umsatz> getAllByMandant(Mandant mandant);
}
