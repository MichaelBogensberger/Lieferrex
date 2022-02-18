package com.htlimst.lieferrex.repository;


import com.htlimst.lieferrex.model.Angestellter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AngestellterRepository extends JpaRepository<Angestellter,Long> {
    List<Angestellter> findByBenutzername(Angestellter angestellter);

}
