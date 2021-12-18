package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Kategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KategorieRepository extends JpaRepository<Kategorie,Long> {
}
