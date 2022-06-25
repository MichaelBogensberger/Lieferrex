package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KundeRepository extends JpaRepository<Kunde,Long>{
    Kunde findByEmail(String email);
}
