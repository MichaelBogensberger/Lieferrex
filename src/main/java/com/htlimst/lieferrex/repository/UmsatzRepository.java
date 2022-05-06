package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Umsatz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UmsatzRepository extends JpaRepository<Umsatz,Long> {
    public Umsatz getUmsatzByMandant(Mandant mandant);
}
