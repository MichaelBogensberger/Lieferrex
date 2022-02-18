package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Rolle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolleRepository extends JpaRepository<Rolle,Long> {
    List<Rolle> findByRolle(Rolle rolle);
}
