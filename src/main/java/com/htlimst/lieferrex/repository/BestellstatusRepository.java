package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Bestellstatus;
import com.htlimst.lieferrex.model.enums.BestellstatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestellstatusRepository extends JpaRepository<Bestellstatus,Long> {
    Bestellstatus getBestellstatusByBestellstatus(BestellstatusEnum bestellstatusEnum);
}
