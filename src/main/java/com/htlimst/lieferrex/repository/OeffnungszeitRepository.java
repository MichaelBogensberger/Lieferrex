package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Oeffnungszeit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OeffnungszeitRepository extends JpaRepository<Oeffnungszeit,Long> {
}
