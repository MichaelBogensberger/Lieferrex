package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Mandant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MandnatRepository extends JpaRepository<Mandant,Long> {
}
