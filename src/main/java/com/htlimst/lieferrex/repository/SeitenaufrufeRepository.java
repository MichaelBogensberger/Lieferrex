package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Seitenaufrufe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeitenaufrufeRepository extends JpaRepository<Seitenaufrufe, Long> {
    Seitenaufrufe getSeitenaufrufeByMandant(Mandant mandant);
}
