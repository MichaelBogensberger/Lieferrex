package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Bestellstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestellstatusRepository extends JpaRepository<Bestellstatus,Long> {
}
